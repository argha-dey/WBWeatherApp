package com.cyberswift.weatherapp.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.callback.AlertDialogCallBack;
import com.cyberswift.weatherapp.model.UserClass;

import java.io.IOException;


public class Util {

    private static String USERCLASS_PREF_NAME = "USERCLASS_PREF_NAME";
    private static String DAMRECORDS_PREF_NAME = "DAMERECORDS_PREF_NAME";
    private static String USERCLASS = "USERCLASS";
    private static String DAMERECORDS = "DAMERECORDS";

    //TODO Saving UserClass details

    public static void saveUserClass(final Context mContext, UserClass userClass) {
        System.out.println("-------------->>    -----------\nSave user Class called\n\n--------------------------->>>");
        SharedPreferences IMISPrefs = mContext.getSharedPreferences(USERCLASS_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = IMISPrefs.edit();
        try {
            prefsEditor.putString(USERCLASS, ObjectSerializer.serialize(userClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefsEditor.commit();

    }


    public static boolean isInternetPresent(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    // TODO Fetching UserClass details

    public static UserClass fetchUserClass(final Context mContext) {
        SharedPreferences IMISPrefs = mContext.getSharedPreferences(USERCLASS_PREF_NAME, Context.MODE_PRIVATE);
        UserClass userClass = null;
        String serializeOrg = IMISPrefs.getString(USERCLASS, null);
        try {
            if (serializeOrg != null) {
                userClass = (UserClass) ObjectSerializer.deserialize(serializeOrg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userClass;
    }
    /*---------------------------------------------------------------------------------------------*/
/*    //TODO Saving DamRecord details

    public static void saveDamRecords(final Context mContext, DamRecords dr) {
        System.out.println("-------------->>    -----------\nSave DamREcords class called\n\n--------------------------->>>");
        SharedPreferences IMISPrefs = mContext.getSharedPreferences(DAMRECORDS_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = IMISPrefs.edit();
        try {
            prefsEditor.putString(DAMERECORDS, ObjectSerializer.serialize(dr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefsEditor.commit();
    }
    // TODO Fetching DamRecord details

    public static DamRecords fetchDamRecords(final Context mContext) {
        SharedPreferences IMISPrefs = mContext.getSharedPreferences(DAMRECORDS_PREF_NAME, Context.MODE_PRIVATE);
        DamRecords dr = null;
        String serializeOrg = IMISPrefs.getString(DAMERECORDS, null);
        try {
            if (serializeOrg != null) {
                dr = (DamRecords) ObjectSerializer.deserialize(serializeOrg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dr;
    }*/

    /*----------------------------------------------------------------------------------------------*/
    public static void showMessageWithOk(final Context mContext, final String message) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {
                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);

                alert.setMessage(message);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });
    }

    public static void showCallBackMessageWithOkCancel(final Context mContext, final String message, final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {
                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

                alert.setTitle(R.string.app_name);

                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onSubmit();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onCancel();
                    }
                });
                alert.show();
            }
        });
    }

    public static void customShowCallBackMessageWithOkCancel(final Context mContext, final String message, final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {


            }
        });
    }    }