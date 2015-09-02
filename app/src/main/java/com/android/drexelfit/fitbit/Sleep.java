package com.android.drexelfit.fitbit;

/**
 * Created by KUSHAGRA on 3/15/2015.
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONObject;

import com.temboo.Library.Fitbit.Sleep.GetSleep;
import com.temboo.core.TembooSession;

/**
 * An AsyncTask that will be used to interact with Temboo
 */
public class Sleep
{
    public static int getCurrentSleepMinutes()
    {
    	int totalSleepMin = 0;
    	
    	try 
    	{
            // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
            TembooSession session = new TembooSession("kush19920508", "myFirstApp", "061162d4909c4ff6941f019c09c33c5b");
            GetSleep getSleepChoreo = new GetSleep(session);
            // Get an InputSet object for the choreo
            GetSleep.GetSleepInputSet getSleepInputs = getSleepChoreo.newInputSet();

            String dt = "2015-01-03";  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dt));

            Integer SumofSleep = 0;

            for (int t = 1; t < 8; t++)
            {

                dt = sdf.format(c.getTime());

                // Set inputs
                getSleepInputs.set_Date(dt);
                getSleepInputs.set_AccessToken("7147a32fe61ff5f22e7ad93aa67ea410");
                getSleepInputs.set_AccessTokenSecret("30f94c25275a324bba01ce52a4e1e77d");
                getSleepInputs.set_ConsumerSecret("6e7aabfb52ab43c2a04fb8fe19bd4ce1");
                getSleepInputs.set_ConsumerKey("5d8952753b054b1d862e0019b420f423");

                // Execute Choreo
                GetSleep.GetSleepResultSet getSleepResults = getSleepChoreo.execute(getSleepInputs);
                JSONObject obj = new JSONObject(getSleepResults.get_Response());
                JSONObject fct2 = obj.getJSONObject("summary");
                //JSONObject fct3 = fct2.getJSONObject(k);
                Integer txt = (Integer) fct2.getInt("totalMinutesAsleep");

                SumofSleep = txt + SumofSleep;
                c.add(Calendar.DATE, 1);  // number of days to add


            }
            
            totalSleepMin = SumofSleep;
        } catch(Exception e) {
        	//Log.e(this.getClass().toString(), e.getMessage());
        }
    	
    	return totalSleepMin;
    }
}

