package com.android.drexelfit;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.drexelfit.adapter.RewardsAdapter;
import com.android.drexelfit.db.MySQLiteHelper;
import com.android.drexelfit.db.Reward;
import com.android.drexelfit.db.User;

public class FragmentRewards extends Fragment 
{
	private ListView mListView;
	private RewardsAdapter mAdapter;
	private ProgressBar mSpinner;
	private ArrayList<Reward> mRewards;

	private MySQLiteHelper mDb;
	
	private User mUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_rewards, container, false);
		init(rootView);
		return rootView;
	}
	
	private void init(View view)
	{
		mDb = new MySQLiteHelper(getActivity());

		mListView = (ListView) view.findViewById(R.id.lv_rewards);
		mListView.setOnItemClickListener(mOnItemClick);
		
		mSpinner = (ProgressBar) view.findViewById(R.id.progress);
	}
	
	public void loadData(User user)
	{
		mUser = user;
		
		// load rewards
		new RewardsLoader().loadRewards();
	}
	
	private OnItemClickListener mOnItemClick = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			final Reward reward = mRewards.get(position);
			final int points = mUser.getPoints();
			
			if (points < reward.getCost()) 
			{
				Toast.makeText(getActivity(), "You don't have enough points :/", Toast.LENGTH_SHORT).show();
			}
			else if (reward.getWasRedeemed())
			{
				Toast.makeText(getActivity(), "Reward already redeemed", Toast.LENGTH_SHORT).show();
			}
			else
			{
				// build the popup and show it
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		        builder.setTitle("Redeem Reward");
		        builder.setMessage("Would you like to redeem this reward?");
		        builder.setCancelable(true);
		        builder.setNegativeButton("No", null);
		        builder.setPositiveButton("Yes", new OnClickListener() 
		        {	
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						//TODO check if user can redeem reward
						reward.setWasRedeemed(true);
						mDb.updateFood(reward);
						mAdapter.notifyDataSetChanged();
						
						mUser.setPoints(points - reward.getCost());
					}
				});
		        
		        AlertDialog alert = builder.create();
		        alert.show();
			}
			
		}
	};
	
	private class RewardsLoader extends AsyncTask<Void, Void, Integer>
	{		
		// Newly added below...
		private List<List<String>> allRewards;
		private Rewards rewards;
		private String website = "http://www.drexelcampusdining.com/Hans3.9.15.htm";
				
		public void loadRewards()
		{
			mSpinner.setVisibility(View.VISIBLE);
			execute();
		}
		
		@Override
		protected Integer doInBackground(Void... params) 
		{
			int result = -1;
			
			// Declarations
			mRewards = new ArrayList<Reward>();
			rewards = new Rewards();
			allRewards = new ArrayList<List<String>>();
			
			// Get raw list of all menu items from web
			rewards.retMenu(website);
			
			// Parse menu items for needed info and retrieve this list
			rewards.parseMenu();
			allRewards = rewards.getRewards();
			
			// empty db
			mDb.emptyDatabases();
			
			//TODO only add to db after X amount of time (1hr?)
			for(int i = 0; i< allRewards.size(); i++)
			{
				//db.addFood(new Food(rewards.getRewards().get(i).get(0),  rewards.getRewards().get(i).get(1),  -1,  rewards.getRewards().get(i).get(2),  rewards.getRewards().get(i).get(3)));
				mDb.addFood(new Reward(allRewards.get(i).get(0),
									allRewards.get(i).get(1),  
									rewards.calculateFoodCost(Integer.parseInt(allRewards.get(i).get(1))),								//-1,
									allRewards.get(i).get(2),  
									allRewards.get(i).get(3)));
			}
			
			//Troy's food ArrayList
			//List<Reward> troyFoods = new ArrayList<Reward>();
			mRewards = mDb.getAllFoods();
			
			result = 1;
			
			return result;
		}
		
		@Override
		protected void onPostExecute(Integer result) 
		{
			mSpinner.setVisibility(View.GONE);
			
			if (result > 0) 
			{
				mAdapter = new RewardsAdapter(mRewards, getActivity());
				mListView.setAdapter(mAdapter);
			}
		}
	}
}
