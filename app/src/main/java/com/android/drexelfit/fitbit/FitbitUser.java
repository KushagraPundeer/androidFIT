package com.android.drexelfit.fitbit;

/**
 * Created by KUSHAGRA on 3/15/2015.
 */
 import org.json.JSONObject;

import android.util.Log;

import com.android.drexelfit.db.User;
import com.temboo.Library.Fitbit.Profile.GetUserInfo;
import com.temboo.core.TembooSession;

/**
 * An AsyncTask that will be used to interact with Temboo
 */
public class FitbitUser
{
	public static User getFitbitUser()
	{
		User user = new User();

		try {
            // Temboo code will go here
            // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
            TembooSession session = new TembooSession("kush19920508", "myFirstApp", "061162d4909c4ff6941f019c09c33c5b");

            GetUserInfo getUserInfoChoreo = new GetUserInfo(session);

            // Get an InputSet object for the choreo
            GetUserInfo.GetUserInfoInputSet getUserInfoInputs = getUserInfoChoreo.newInputSet();

            // Set inputs
            getUserInfoInputs.set_AccessToken("7147a32fe61ff5f22e7ad93aa67ea410");
            getUserInfoInputs.set_AccessTokenSecret("30f94c25275a324bba01ce52a4e1e77d");
            getUserInfoInputs.set_ConsumerSecret("6e7aabfb52ab43c2a04fb8fe19bd4ce1");
            getUserInfoInputs.set_ConsumerKey("5d8952753b054b1d862e0019b420f423");

            // Execute Choreo
            GetUserInfo.GetUserInfoResultSet getUserInfoResults = getUserInfoChoreo.execute(getUserInfoInputs);
            JSONObject obj = new JSONObject(getUserInfoResults.get_Response());
            JSONObject fct2 = obj.getJSONObject("user");
            String name = (String) fct2.get("fullName");
            Integer weight = (Integer) fct2.get("weight");
            double strideLength = fct2.optDouble("strideLengthWalking");
            
            user.setName(name);
            user.setWeight(weight);
            user.setStrideLengthWalking(strideLength);
        } catch(Exception e) {
            Log.e("FITBIT", e.getMessage());
        }
		
		return user;
	}
}


