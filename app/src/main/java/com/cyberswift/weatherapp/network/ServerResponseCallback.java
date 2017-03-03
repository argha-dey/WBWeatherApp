package com.cyberswift.weatherapp.network;

import org.json.JSONObject;

public interface ServerResponseCallback {

    public void onSuccess(JSONObject resultJsonObject);

    public void onError();

}