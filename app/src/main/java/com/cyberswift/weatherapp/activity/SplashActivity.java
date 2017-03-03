package com.cyberswift.weatherapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.util.Util;


public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        new SplashTimerTask().execute();
    }
    private class SplashTimerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(Util.fetchUserClass(SplashActivity.this) == null) {
                openLoginActivity();
            } else {
                if (Util.fetchUserClass(SplashActivity.this).getIsRemember()) {
                    openHomeActivity();
                } else {
                    openLoginActivity();
                }
            }

        }
    }

    public void openLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}



