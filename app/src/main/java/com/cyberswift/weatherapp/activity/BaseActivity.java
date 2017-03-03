package com.cyberswift.weatherapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.callback.AlertDialogCallBack;
import com.cyberswift.weatherapp.network.VolleyTaskManager;

import java.util.HashMap;




public class BaseActivity extends Activity  {
    private Context mContext;
    public PopupWindow popupWindow;
    public ProgressDialog mProgressDialog;
    public VolleyTaskManager volleyTaskManager;
    Boolean isDistrictService = false, isStationService = false, isRiverBasinService = false, isRainType = false, isgetAllRainfallData = false,
            isFinalSubmit = false,isRiverDistrictService=false,isRiverStationService=false,isRiverNameService=false,isRiverTrend=false,getAllRivergaugeData=false,
            isRiverFinalSubmit=false,isFloodRiverService =false, isgetReservoirService=false,isFloodLevelDataService=false,isAllFloodDataService =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(BaseActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mContext = BaseActivity.this;
        volleyTaskManager = new VolleyTaskManager(BaseActivity.this);
    }


    /************************custom dialog box ****************************/

    public void showDialog(Activity activity, String msg, final AlertDialogCallBack callBack){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.textView_custom);
        text.setText(msg);

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.button_cancel);



       dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBack.onCancel();

                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.button_ok);

       dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onSubmit();


                dialog.cancel();
            }
        });

        dialog.show();
    }
/***************************** Show custom Toast message **********************************/
    public void showCustomToastAlert(Context context,String msg, int resId)
    {
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getLayoutInflater();

        // Call toast.xml file for toast layout
        View toastRoot		= inflater.inflate(R.layout.new_custom_toast, null);
        TextView toastMsg	= (TextView) toastRoot.findViewById(R.id.toastMsg);
        //ImageView toastImg	= (ImageView) toastRoot.findViewById(R.id.toastImg);

        toastMsg.setText(""+msg);
       // toastImg.setImageResource(resId);

        Toast toast = new Toast(context);

        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public void showCustomToastAlertStressful(Context context, String msg, int resId)
    {
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getLayoutInflater();

        // Call toast.xml file for toast layout
        View toastRoot		= inflater.inflate(R.layout.toast, null);
        TextView toastMsg	= (TextView) toastRoot.findViewById(R.id.toastMsg);
        ImageView toastImg	= (ImageView) toastRoot.findViewById(R.id.toastImg);

        toastMsg.setText(""+msg);
        toastImg.setImageResource(resId);

        Toast toast = new Toast(context);

        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
/****************************** webservice for Rain Gauge **********************/

    protected void DistrictWebserviceCalling() {
        isDistrictService = true;
        volleyTaskManager.doGetDistrict();
    }

    public void stationWebserviceCalling(String DISTRICT_ID) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isStationService = true;

        paramsMap.put("DISTRICT_ID", DISTRICT_ID);
        Log.d("tag", "DISTRICT_ID " + DISTRICT_ID);
        volleyTaskManager.doGetStation(paramsMap);
    }

    public void riverBasinWebserviceCalling(String riverBasinName) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isRiverBasinService = true;

        paramsMap.put("RAIN_STATION", riverBasinName);
        Log.d("tag", "RAIN_STATION " + riverBasinName);
        volleyTaskManager.doGetRiverBasine(paramsMap);
    }

    protected void rainTypeWebserviceCalling() {
        isRainType = true;
        volleyTaskManager.doGetType();
    }

    public void rainfallDatagetWebserviceCalling(String rainStation, String riverBasinId) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isgetAllRainfallData = true;

        paramsMap.put("RAIN_STATION", rainStation);
        paramsMap.put("RIVER_BASIN_ID", riverBasinId);

        // tag name must be care full entery
        Log.d("tag", "RAIN_STATION " + rainStation);

        volleyTaskManager.doGetRainfalldata(paramsMap);
    }

    public void submitRainfallWebserviceCalling(String rainGaugeId, String dailyRainfall, String remarks) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isFinalSubmit = true;

        paramsMap.put("RAIN_GAUGE_ID", rainGaugeId);
        paramsMap.put("DAILY_RAINFALL", dailyRainfall);
        paramsMap.put("REMARKS", remarks);

        // tag name must be care full entery
        Log.d("tag", "RAIN_STATION " + remarks);

        volleyTaskManager.doSubmitRainfalldata(paramsMap);
    }
/*------------------------------- webservice RiverGauge --------------------------*/

    public void riverDistrictWebserviceCalling() {
        isRiverDistrictService = true;
        volleyTaskManager.doGetRiverDistrict();
    }
    public void RiverStationWebserviceCalling(String DISTRICT_ID) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isRiverStationService = true;

        paramsMap.put("DISTRICT_ID", DISTRICT_ID);
        Log.d("tag", "DISTRICT_ID " + DISTRICT_ID);
        volleyTaskManager.doGetRiverStation(paramsMap);
    }
    public void riverNameWebserviceCalling(String riverGaugeId) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isRiverNameService = true;

        paramsMap.put("GAUGE_ID", riverGaugeId);
        Log.d("tag", "GAUGE_ID " + riverGaugeId);
        volleyTaskManager.doGetRiverName(paramsMap);
    }


    public void riverTrendWebserviceCalling() {
        isRiverTrend = true;
        volleyTaskManager.doGetTrend();
    }

    public void getriverGaugeDataWebserviceCalling(String GAUGE_ID, String RIVER_ID) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        getAllRivergaugeData = true;

        paramsMap.put("GAUGE_ID", GAUGE_ID);
        paramsMap.put("RIVER_ID", RIVER_ID);
        volleyTaskManager.doGetRiverGaugefalldata(paramsMap);
    }
    public void submitRainfallWebserviceCalling(String riverGaugeId, String gaugeID,String level, String remarks) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isRiverFinalSubmit = true;

        paramsMap.put("RIVER_GAUGE_ID", riverGaugeId);
        paramsMap.put("TREND", gaugeID);
        paramsMap.put("GAUGE_LEVEL", level);
        paramsMap.put("REMARKS",remarks);

        // tag name must be care full entery
        Log.d("tag", "RAIN_STATION " + remarks);

        volleyTaskManager.doSubmitRiverGaugedata(paramsMap);
    }
    /*---------------------------------------Daily Flood Report -----------------------------------------------------*/

    protected void RiverBasinWebserviceCalling() {
        isFloodRiverService = true;
        volleyTaskManager.doGetFloodRiverBasin();
    }

    public void FloodReservoirWebserviceCalling(String riverBasinId) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isgetReservoirService = true;

        paramsMap.put("RIVER_BASIN_ID", riverBasinId);

        // tag name must be care full entery
        Log.d("tag", "RAIN_STATION " + riverBasinId);

        volleyTaskManager.doGetReservoir(paramsMap);
    }

    public void FloodLevelWebserviceCalling(String RiverId, String ReservoirName) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isFloodLevelDataService = true;

        paramsMap.put("RIVERBASINID", RiverId);
        paramsMap.put("RESERVOIRNAME", ReservoirName);
        volleyTaskManager.doGetFloodLevel(paramsMap);
    }
    

    public void submitAllDataWebserviceCalling(String FloodId, String PresentLevel,String Inflow,String RiverDischarge,String Time,String Remarks) {

        HashMap<String, String> paramsMap = new HashMap<String, String>();
        isAllFloodDataService = true;

        paramsMap.put("FLOOD_ID", FloodId);
        paramsMap.put("PRESENT_LEVEL", PresentLevel);

        paramsMap.put("INFLOW", Inflow);
        paramsMap.put("RIVER_DISCHARGE", RiverDischarge);

        paramsMap.put("TIME", Time);
        paramsMap.put("REMARKS", Remarks);

        volleyTaskManager.doGetFloodAllData(paramsMap);
    }
/*-----------------------------------------------------------------------------------*/

}
