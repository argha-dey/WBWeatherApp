package com.cyberswift.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class RiverGaugeDB implements DBConstants{

    private static RiverGaugeDB obj = null;

    public synchronized static RiverGaugeDB obj() {

        if (obj == null)
            obj = new RiverGaugeDB();
        return obj;

    }

    public Boolean saveRiverGaugeData(Context context, ContentValues cv) {

        System.out.println(" ----------  Save River Gauge Records Data  --------- ");

        SQLiteDatabase mdb = WeatherDatabaseHandler.getInstance(context).getWritableDatabase();
        mdb.beginTransaction();
        try {

            mdb.insert(RIVER_GAUGE_DATA_RECORDS_TABLE, null, cv);
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
