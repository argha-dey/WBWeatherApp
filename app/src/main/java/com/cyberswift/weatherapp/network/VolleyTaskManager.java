package com.cyberswift.weatherapp.network;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.cyberswift.weatherapp.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cyberswift.weatherapp.application.WeatherApp;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


@SuppressLint("ShowToast")
public class VolleyTaskManager extends ServiceConnector {
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String TAG = "";
    private String tag_json_obj = "jobj_req";
    private boolean isToShowDialog = true, isToHideDialog = true;

    private static int mStatusCode;
    private static ProgressDialog progressdialog;
    private static Activity activity;

    public VolleyTaskManager(Context context) {
        mContext = context;

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");

        TAG = mContext.getClass().getSimpleName();
        Log.d("tag", TAG);
    }

    public void showProgressDialog() {
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    /**
     *
     * Making json object request
     * */
    private void makeJsonObjReq(int method, String url, final Map<String, String> paramsMap) {
        if (isToShowDialog) {
            showProgressDialog();
        }

        Log.v("JSONObject", new JSONObject(paramsMap).toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method, url, new JSONObject(paramsMap), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                // msgResponse.setText(response.toString());
                if (isToHideDialog) {
                    hideProgressDialog();
                }
                // TODO On getting successful result:
                ((ServerResponseCallback) mContext).onSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.d("error ocurred", "TimeoutError");
                    Toast.makeText(mContext, mContext.getString(R.string.response_timeout), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Log.d("error ocurred", "AuthFailureError");
                    Toast.makeText(mContext, mContext.getString(R.string.auth_failure), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Log.d("error ocurred", "ServerError");
                    Toast.makeText(mContext, mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Log.d("error ocurred", "NetworkError");
                    Toast.makeText(mContext, mContext.getString(R.string.network_error), Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Log.d("error ocurred", "ParseError");
                    Toast.makeText(mContext, mContext.getString(R.string.parse_error), Toast.LENGTH_LONG).show();
                }

                ((ServerResponseCallback) mContext).onError();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {

                return paramsMap;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        WeatherApp.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    /**
     * Service method calling for Login -->
     **/

    public void doLogin(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "MobileUserLogIn";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }
    /*---------------------------------Service method calling for RainGauge--------------------------------------------*/
    /**
     * Service method calling for RainGauge -->
     **/
    public void doGetDistrict() {
        String url = getBaseURL() + "GetDistrictName";
        int method = Method.GET;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, new HashMap<String, String>());
    }


    public void doGetStation(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetRainGaugeStation";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    public void doGetRiverBasine(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetRiverBasin";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }


    public void doGetType() {
        String url = getBaseURL() + "GetRiverType";
        int method = Method.GET;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, new HashMap<String, String>());
    }


    public void doGetRainfalldata(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetAnnualRainData";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    public void doSubmitRainfalldata(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "SaveDailyRainGaugeData";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    /*-------------------------------------------Service method calling for RiverGauge-----------------------------------------------*/

    public void doGetRiverDistrict() {
        String url = getBaseURL() + "GetRiverDistrictName";
        int method = Method.GET;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, new HashMap<String, String>());
    }

    public void doGetRiverStation(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetRiverGaugeStation";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    public void doGetRiverName(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetRiverByGaugeStation";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    public void doGetTrend() {
        String url = getBaseURL() + "GetTrend";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, new HashMap<String, String>());
    }


    public void doGetRiverGaugefalldata(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetAnnualRiverData";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }
    public void doSubmitRiverGaugedata(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "SaveDailyRiverGaugeData";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

/*-------------------------------------------------------------------------------------*/
    public void doGetFloodRiverBasin() {
        String url = getBaseURL() + "GetRiverBasinName";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, new HashMap<String, String>());
    }
    public void doGetReservoir(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetReservoirNameByRiverBasinID";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    public void doGetFloodLevel(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "GetAnnualFloodDataByReservoirNameRiverBasin";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

    public void doGetFloodAllData(HashMap<String, String> paramsMap) {
        String url = getBaseURL() + "SaveDailyFloodData";
        int method = Method.POST;

        Log.i("url", url);
        // System.out.println(paramsMap);
        makeJsonObjReq(method, url, paramsMap);
    }

}
