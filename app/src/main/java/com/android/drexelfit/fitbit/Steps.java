package com.android.drexelfit.fitbit;

/**
 * Created by KUSHAGRA on 3/15/2015.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import com.temboo.Library.Fitbit.Statistics.GetTimeSeriesByDateRange;
import com.temboo.core.TembooSession;

/**
 * An AsyncTask that will be used to interact with Temboo
 */
public class Steps
{
    public static int getCurrentSteps()
    {
    	int steps = 0;
    	try {
            // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
            TembooSession session = new TembooSession("kush19920508", "myFirstApp", "061162d4909c4ff6941f019c09c33c5b");

            GetTimeSeriesByDateRange getTimeSeriesByDateRangeChoreo = new GetTimeSeriesByDateRange(session);

            // Get an InputSet object for the choreo
            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeInputSet getTimeSeriesByDateRangeInputs = getTimeSeriesByDateRangeChoreo.newInputSet();

            String dt = "2015-01-03";  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dt));

            for(int s = 0; s < 7; s++)
            {
                dt = sdf.format(c.getTime());
                c.add(Calendar.DATE, 1);
                System.out.println(dt);
            }

            // Set inputs
            getTimeSeriesByDateRangeInputs.set_EndDate(dt);
            getTimeSeriesByDateRangeInputs.set_StartDate("2015-01-03");
            getTimeSeriesByDateRangeInputs.set_AccessTokenSecret("a86cc40dbc1138e1aafc8e4434e981f6");
            getTimeSeriesByDateRangeInputs.set_ResourcePath("activities/log/steps");
            getTimeSeriesByDateRangeInputs.set_AccessToken("7147a32fe61ff5f22e7ad93aa67ea410");
            getTimeSeriesByDateRangeInputs.set_AccessTokenSecret("30f94c25275a324bba01ce52a4e1e77d");
            getTimeSeriesByDateRangeInputs.set_ConsumerSecret("6e7aabfb52ab43c2a04fb8fe19bd4ce1");
            getTimeSeriesByDateRangeInputs.set_ConsumerKey("5d8952753b054b1d862e0019b420f423");


            // Execute Choreo
            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeResultSet getTimeSeriesByDateRangeResults = getTimeSeriesByDateRangeChoreo.execute(getTimeSeriesByDateRangeInputs);
            JSONObject obj = new JSONObject(getTimeSeriesByDateRangeResults.get_Response());
            JSONArray fct2 = obj.getJSONArray("activities-log-steps");

            ArrayList<String> mylist = new ArrayList<String>();

            for (int k = 0; k < fct2.length(); k++) {
                JSONObject fct3 = fct2.getJSONObject(k);
                String txt = (String) fct3.get("value");
                String Date_time = (String) fct3.get("dateTime");
                mylist.add(txt);

            }

            Integer sum = 0;
            for(int k = 1; k < mylist.size(); k++) {
                sum += Integer.parseInt(mylist.get(k));

            }

            //return sum.toString() + "#" + mylist.toString() ;
            steps = sum;
            
        } catch(Exception e) {
        	//Log.e(this.getClass().toString(), e.getMessage());
        }
    	
    	return steps;
    }
}
