package com.cyberswift.weatherapp.db;

import android.os.Environment;

public interface DBConstants {

    /**
     * Database Name
     */


    // public static String DB_NAME = "WEATHER.db";

    /*for external storage*/

    public static final String DB_NAME = Environment.getExternalStorageDirectory() + "/WEATHER.db";


    /**
     * Database version
     */
    public static final int DB_VERSION = 1;

    public static final String _ID = "_id";

    final String CREATE_TABLE_BASE = "CREATE TABLE IF NOT EXISTS ";

    final String ON = " ON ";

    final String PRIMARY_KEY = " PRIMARY KEY";

    final String INTEGER = " Integer";

    final String TEXT = " TEXT";

    final String DATE_TIME = " DATETIME";

    final String BLOB = " BLOB";

    final String AUTO_INCREMENT = " AUTOINCREMENT";

    final String UNIQUE = "UNIQUE";

    final String START_COLUMN = " ( ";

    final String FINISH_COLUMN = " ) ";

    final String COMMA = ",";

    final String ON_CONFLICT_REPLACE = "ON CONFLICT REPLACE";

    /*
    * River Gauge Data  records table */

    public static final String RIVER_GAUGE_DATA_RECORDS_TABLE = " riverGaugeDataRecordsTable";


    public static final String RIVER_DISTRICT_NAME = "riverDistrictName";
    public static final String RIVER_GAUGE_STATION_NAME = "stationName";
    public static final String NAME_OF_RIVER = "riverName";
    public static final String DIVISIONAL_JURISDICTION = "divisionalJurisdiction";
    public static final String RIVER_GAUGE_LEVEL = "riverGaugeLevel";
    public static final String RIVER_GAUGE_TEND = "riverTrend";
    public static final String RIVER_GAUGE_DL = "riverDL";
    public static final String RIVER_GAUGE_EDL = "riverEDL";
    public static final String RIVER_GAUGE_REMARKS = "remarks";



    /*
    *RAIN Gauge records  Table*/

    public static final String RAIN_GAUGE_RECORDS_TABLE = " rainGaugeRecordsTable";


    public static final String DISTRICT_NAME = "districtName";
    public static final String RAIN_GAUGE_STATION_NAME = "stationName";
    public static final String RIVER_BASIN = "riverBasin";
    public static final String TYPE_OF_RAIN = "rainType";
    public static final String RAINFALL_IN_LAST24HRS = "rainfallInLast24Hrs";
    public static final String CUM_RAINFALL_FROM_1ST_JANUARY= "rainfallFrom1stJanuary";
    public static final String CUM_RAINFALL_FROM_1ST_JUNE = "rainfallFrom1stJune";
    public static final String DISTRICTWISE_ANNUAL_RAINFLL= "annualRainfall";
    public static final String JURISDICTIVE_DIVIN_VALUE_FLOOD= "divnFloodValue";
    public static final String REMARKS = "remarks";



}
