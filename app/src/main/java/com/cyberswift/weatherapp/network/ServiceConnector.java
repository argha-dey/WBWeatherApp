package com.cyberswift.weatherapp.network;


import org.json.JSONObject;

abstract class ServiceConnector {

    // LOCAL SERVER1
    public static final String BASE_URL = "http://192.168.1.117/DisasterWeatherServices/MobileService.svc/";

    // LOCAL SERVER2
    //	public static final String BASE_URL = "http://192.168.1.89/FMSService/MobileAppService.svc/";

    // LIVE TESTING
    //	public static final String BASE_URL = "http://107.22.237.165/FMSTestService/MobileAppService.svc/";

    // LIVE SERVER
   // public static final String BASE_URL = "";
    protected JSONObject outputJson;

    public static String getBaseURL() {
        return BASE_URL;
    }

    public JSONObject getOutputJson() {
        return outputJson;
    }

    public void setOutputJson(JSONObject outputJson) {
        this.outputJson = outputJson;
    }

}
