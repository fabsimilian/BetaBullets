package com.fabsimilian.betabullets;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by tafabs on 26.06.15.
 */
public class BetaBulletsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_application_id));
//        ParseInstallation.getCurrentInstallation().saveInBackground();

        Parse.initialize(this, "PAMv8xp8iY2X1iQK0ws8Fz9j6XpSrSLbI0JNF341", "li7o1zL3dRZBD4aUNT1JM8tVjnUJPCihy1poFR5j");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
