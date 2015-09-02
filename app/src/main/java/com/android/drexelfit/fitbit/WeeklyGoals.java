package com.android.drexelfit.fitbit;

import android.util.Log;

import com.android.drexelfit.db.Goal;
import com.temboo.core.TembooSession;


public class WeeklyGoals 
{
     public static Goal getWeeklyGoal()
     {
    	 Goal goal = new Goal();
    	 
    	 try {
             // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
             TembooSession session = new TembooSession("kush19920508", "myFirstApp", "061162d4909c4ff6941f019c09c33c5b");

             com.temboo.Library.Fitbit.Activities.GetActivityWeeklyGoals getActivityWeeklyGoalsChoreo = new com.temboo.Library.Fitbit.Activities.GetActivityWeeklyGoals(session);

             // Get an InputSet object for the choreo
             com.temboo.Library.Fitbit.Activities.GetActivityWeeklyGoals.GetActivityWeeklyGoalsInputSet getActivityWeeklyGoalsInputs = getActivityWeeklyGoalsChoreo.newInputSet();

             // Set inputs
             getActivityWeeklyGoalsInputs.set_AccessToken("7147a32fe61ff5f22e7ad93aa67ea410");
             getActivityWeeklyGoalsInputs.set_AccessTokenSecret("30f94c25275a324bba01ce52a4e1e77d");
             getActivityWeeklyGoalsInputs.set_ConsumerSecret("6e7aabfb52ab43c2a04fb8fe19bd4ce1");
             getActivityWeeklyGoalsInputs.set_ConsumerKey("5d8952753b054b1d862e0019b420f423");

             // Execute Choreo
             com.temboo.Library.Fitbit.Activities.GetActivityWeeklyGoals.GetActivityWeeklyGoalsResultSet getActivityWeeklyGoalsResults = getActivityWeeklyGoalsChoreo.execute(getActivityWeeklyGoalsInputs);
             
             goal.setSteps(Integer.parseInt(getActivityWeeklyGoalsResults.get_Steps()));
             goal.setEndTime(getActivityWeeklyGoalsResults.getCompletionTime().getTime());

             //Log.i("TEST", "Fitbit Status: " + getActivityWeeklyGoalsResults.getCompletionStatus().toString());
         } catch(Exception e) {
             // if an exception occurred, log it
             //Log.e(this.getClass().toString(), e.getMessage());
         }
    	 
    	 return goal;
     }
}



