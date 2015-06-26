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
        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_application_id));
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
