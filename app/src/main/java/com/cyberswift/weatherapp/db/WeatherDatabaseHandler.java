package com.cyberswift.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WeatherDatabaseHandler extends SQLiteOpenHelper implements DBConstants {
    private static WeatherDatabaseHandler mDatabase;
    private Context mcContext;
    private static final String TAG = "WEATHERDatabaseHandler";

    public WeatherDatabaseHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.mcContext = context;
    }

    public static final WeatherDatabaseHandler getInstance(Context context) {
        if (mDatabase == null) {
            mDatabase = new WeatherDatabaseHandler(context);
            mDatabase.getWritableDatabase();
        }
        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate Database");
        // create table

        String[] createStatements = getCreateTableStatements();
        int total = createStatements.length;
        for (int i = 0; i < total; i++) {
            Log.i(TAG, "executing create" + createStatements[i]);
            Log.i("Database", "Database created");
            sqLiteDatabase.execSQL(createStatements[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String[] getCreateTableStatements() {

        String[] create = new String[3];

        String RAIN_GAUGE_RECORD_LIST = "CREATE TABLE "
                + RAIN_GAUGE_RECORDS_TABLE + "("
                + DISTRICT_NAME + " TEXT,"
                + RAIN_GAUGE_STATION_NAME + " TEXT,"
                + RIVER_BASIN + " TEXT,"
                + TYPE_OF_RAIN + " TEXT ,"
                + RAINFALL_IN_LAST24HRS + " TEXT,"
                + CUM_RAINFALL_FROM_1ST_JANUARY + " TEXT,"
                + CUM_RAINFALL_FROM_1ST_JUNE + " TEXT,"
                + DISTRICTWISE_ANNUAL_RAINFLL + " TEXT,"
                + JURISDICTIVE_DIVIN_VALUE_FLOOD + " TEXT,"
                + REMARKS + " TEXT"
                + ")";

        create[0] = RAIN_GAUGE_RECORD_LIST;

        String RIVER_GAUGE_RECORD_LIST = "CREATE TABLE "
                + RIVER_GAUGE_DATA_RECORDS_TABLE + "("
                + RIVER_DISTRICT_NAME + " TEXT,"
                + RIVER_GAUGE_STATION_NAME + " TEXT,"
                + NAME_OF_RIVER + " TEXT,"
                + DIVISIONAL_JURISDICTION + " TEXT ,"
                + RIVER_GAUGE_LEVEL + " TEXT,"
                + RIVER_GAUGE_TEND + " TEXT,"
                + RIVER_GAUGE_DL + " TEXT,"
                + RIVER_GAUGE_EDL + " TEXT,"
                + RIVER_GAUGE_REMARKS + " TEXT"
                + ")";

        create[1] = RIVER_GAUGE_RECORD_LIST;




        return create;


    }
}
