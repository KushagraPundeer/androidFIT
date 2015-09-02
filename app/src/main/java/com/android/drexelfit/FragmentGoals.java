package com.android.drexelfit;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.drexelfit.db.Goal;
import com.android.drexelfit.db.User;
import com.android.drexelfit.fitbit.FitbitUser;
import com.android.drexelfit.fitbit.Sleep;
import com.android.drexelfit.fitbit.Steps;
import com.android.drexelfit.fitbit.WeeklyGoals;

public class FragmentGoals extends Fragment
{
	private User mUser;
	private Goal[] mGoals;
	
	private TextView mtvUserName;
	private TextView mtvUserWeight;
	private TextView mtvUserPoints;

	private TextView mtvFirstGoal;
	private TextView mtvFirstGoalProgress;
	private TextView mtvSecondGoal;
	private TextView mtvSecondGoalProgress;

	private TextView mtvGoalsEndDate;

	private LinearLayout mContentCover;
	
	private OnLoadCompleteListener mOnLoadComplete;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{		
		View rootView = inflater.inflate(R.layout.fragment_goals, container, false);
		init(rootView);
		return rootView;
	}
	
	private void init(View view)
	{
		mGoals = new Goal[2];
		mGoals[0] = new Goal();
		mGoals[1] = new Goal();
		
		mContentCover = (LinearLayout) view.findViewById(R.id.content_cover);
		
		mtvUserName = (TextView) view.findViewById(R.id.goals_user_name);
		mtvUserWeight = (TextView) view.findViewById(R.id.goals_user_weight);
		mtvUserPoints = (TextView) view.findViewById(R.id.goals_user_points);

		mtvFirstGoal = (TextView) view.findViewById(R.id.goals_first_goal);
		mtvSecondGoal = (TextView) view.findViewById(R.id.goals_second_goal);

		mtvFirstGoalProgress = (TextView) view.findViewById(R.id.goals_first_goal_progress);
		mtvSecondGoalProgress = (TextView) view.findViewById(R.id.goals_second_goal_progress);
		
		mtvGoalsEndDate = (TextView) view.findViewById(R.id.goals_end_date);
		
		new GoalsLoader().loadGoals();
	}
	
	public User getUser()
	{
		return mUser;
	}
	
	public Goal[] getGoals()
	{
		return mGoals;
	}
	
	public void refreshLayout()
	{
		// user
		mtvUserName.setText(mUser.getName());
		mtvUserWeight.setText("Weight: " + mUser.getWeight() + " kg");
		mtvUserPoints.setText("Calorie Points: " + mUser.getPoints());
		
		// first goal
		mtvFirstGoal.setText("Walk " + mGoals[0].getSteps() + " Steps");
		mtvFirstGoalProgress.setText("Progress: " + mGoals[0].getProgress() + "/" + mGoals[0].getSteps() + " Steps");
		
		// second goal
		mtvSecondGoal.setText("Sleep " + mGoals[1].getSleepMinutes()/60 + " Hours"); //TODO converting to hours
		mtvSecondGoalProgress.setText("Progress: " + mGoals[1].getProgress()/60 + "/" + mGoals[1].getSleepMinutes()/60 + " Hrs");
	
		// goal completion date
		Date date = new Date(mGoals[0].getEndTime());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss");
		mtvGoalsEndDate.setText("Ends on: " + sdf.format(date));
	}
	
	public void setOnLoadCompleteListener(OnLoadCompleteListener ls)
	{
		mOnLoadComplete = ls;
	}
	
	private class GoalsLoader extends AsyncTask<Void, Void, Integer>
	{
		public void loadGoals()
		{
			mContentCover.setVisibility(View.VISIBLE);
			execute();
		}
		
		@Override
		protected Integer doInBackground(Void... params) 
		{			
			mUser = FitbitUser.getFitbitUser();
			mUser.setPoints(500);
			
			// steps goal
			mGoals[0] = WeeklyGoals.getWeeklyGoal();
			int currentSteps = Steps.getCurrentSteps();
			mGoals[0].setProgress(currentSteps);

			// sleep goal
			mGoals[1] = new Goal();
			int currentSleepMinutes = Sleep.getCurrentSleepMinutes();
			mGoals[1].setSleepMinutes(3360);//8hr a day for 5 days TODO: shouldnt hardcode
			mGoals[1].setProgress(currentSleepMinutes);
			
			return 1;
		}
		
		@Override
		protected void onPostExecute(Integer result) 
		{
			mContentCover.setVisibility(View.GONE);
			
			refreshLayout();
			
			if (mOnLoadComplete != null) {
				mOnLoadComplete.onLoaded(mUser);
			}
		}
	}
	
	public interface OnLoadCompleteListener
	{
		public void onLoaded(User user);
	}
}
