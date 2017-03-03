package com.cyberswift.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class RainGaugeDB implements DBConstants {


    private static RainGaugeDB obj = null;

    public synchronized static RainGaugeDB obj() {

        if (obj == null)
            obj = new RainGaugeDB();
        return obj;

    }

    public Boolean saveRainGaugeData(Context context, ContentValues cv) {

        System.out.println(" ----------  Save Rain Gauge  Data  --------- ");

        SQLiteDatabase mdb = WeatherDatabaseHandler.getInstance(context).getWritableDatabase();
        mdb.beginTransaction();
        try {

            mdb.insert(RAIN_GAUGE_RECORDS_TABLE, null, cv);
            mdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mdb.endTransaction();
            return true;
        }

    }
}

