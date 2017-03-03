package com.cyberswift.weatherapp.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.callback.AlertDialogCallBack;
import com.cyberswift.weatherapp.dropdown.DropDownViewForXML;
import com.cyberswift.weatherapp.model.RiverBasin;
import com.cyberswift.weatherapp.network.ServerResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DailyFloodReportActivity extends BaseActivity implements View.OnClickListener, ServerResponseCallback {
    private Context mContext;
    private DropDownViewForXML dropdown_riverBasin, dropdown_reservoir;
    private EditText et_remarks, et_present_reservoir, et_inflow, et_river_discharge, et_flood_time, et_max_flood, et_conservation;
    private ArrayList<RiverBasin> riverBasineArrayList;
    private ArrayList<String> reservoirList;
    private Button btn_submit, btn_reset;
    private String riverBasinId, reservoirName, floodId, message;
    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_flood_records);
        mContext = DailyFloodReportActivity.this;
        init();
    }

    private void init() {
        dropdown_riverBasin = (DropDownViewForXML) findViewById(R.id.dropdown_flood_river_name);
        dropdown_reservoir = (DropDownViewForXML) findViewById(R.id.dropdown_flood_reservoir_name);

        btn_submit = (Button) findViewById(R.id.btn_flood_submit);
        btn_reset = (Button) findViewById(R.id.btn_flood_reset);
        btn_submit.setOnClickListener(this);
        btn_reset.setOnClickListener(this);


        et_max_flood = (EditText) findViewById(R.id.et_max_flood);
        et_conservation = (EditText) findViewById(R.id.et_conservation_level);

        et_present_reservoir = (EditText) findViewById(R.id.et_present_reservoir_level);
        et_present_reservoir.setFilters(new InputFilter[]{new DailyFloodReportActivity.DecimalDigitsInputFilter(10, 2)});

        et_inflow = (EditText) findViewById(R.id.et_inflow);
        et_inflow.setFilters(new InputFilter[]{new DailyFloodReportActivity.DecimalDigitsInputFilter(10, 2)});

        et_river_discharge = (EditText) findViewById(R.id.et_river_discharge);
        et_river_discharge.setFilters(new InputFilter[]{new DailyFloodReportActivity.DecimalDigitsInputFilter(10, 2)});

        // et_flood_time = (AmPmCirclesView)findViewById(R.id.et_flood_time);
        et_remarks = (EditText) findViewById(R.id.et_flood_remarks);
        et_flood_time = (EditText) findViewById(R.id.et_flood_time);
        et_flood_time.setOnClickListener(this);

        et_flood_time.setFocusable(false);

        RiverBasinWebserviceCalling();


        et_remarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_remarks.setHint("");
                } else
                    et_remarks.setHint("Enter your comments here.");

            }
        });


        dropdown_riverBasin.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                et_remarks.setText("");
                et_present_reservoir.setText("");
                et_inflow.setText("");
                et_river_discharge.setText("");
                et_flood_time.setText("");
                et_max_flood.setText("");
                et_conservation.setText("");

                riverBasinId = riverBasineArrayList.get(position).getId();
                Log.d("tag", "RiverBAsinId" + riverBasinId);
                FloodReservoirWebserviceCalling(riverBasinId);


            }
        });

        dropdown_reservoir.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                reservoirName = reservoirList.get(position);
                FloodLevelWebserviceCalling(riverBasinId, reservoirName);


            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.et_flood_time:
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(DailyFloodReportActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                boolean isPM = (hourOfDay >= 12);
                                et_flood_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));


                            }
                        }, hour, minute, false);
                timePickerDialog.show();

                break;
            case R.id.btn_flood_submit:
                if (dropdown_riverBasin.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Please Select RiverBasin", R.drawable.red_warning);


                } else if (dropdown_reservoir.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Please Select Reservoir", R.drawable.red_warning);






                } else if (et_present_reservoir.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext,"Present Reservoir Field is Blank", R.drawable.red_warning);



                } else if (et_inflow.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, " Inflow  Field is Blank ", R.drawable.red_warning);


                } else if (et_river_discharge.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "River Discharge Field is Blank ", R.drawable.red_warning);


                } else if (et_flood_time.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext,  "Flood time Field is Blank", R.drawable.red_warning);
                } else if (et_remarks.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, " Remarks Field is Blank", R.drawable.red_warning);

                } else if (et_max_flood.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Maximum  Field is Blank", R.drawable.red_warning);


               } else if (et_conservation.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Conservation Field is Blank", R.drawable.red_warning);


                } else {
                 /*   Util.showCallBackMessageWithOkCancel(mContext, "Do you want to save data?", new AlertDialogCallBack() {
                        @Override
                        public void onSubmit() {
                            submitAllDataWebserviceCalling(floodId, et_present_reservoir.getText().toString().trim(), et_inflow.getText().toString().trim(), et_river_discharge.getText().toString().trim(), et_flood_time.getText().toString().substring(0, 5).trim(), et_remarks.getText().toString().trim());

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
*/
showDialog(DailyFloodReportActivity.this, "Do you want to save data?", new AlertDialogCallBack() {
    @Override
    public void onSubmit() {
        submitAllDataWebserviceCalling(floodId, et_present_reservoir.getText().toString().trim(), et_inflow.getText().toString().trim(), et_river_discharge.getText().toString().trim(), et_flood_time.getText().toString().substring(0, 5).trim(), et_remarks.getText().toString().trim());

    }

    @Override
    public void onCancel() {

    }
});

                }

                break;
            case R.id.btn_flood_reset:
                dropdown_riverBasin.setText("");
                dropdown_reservoir.setText("");
                et_remarks.setText("");
                et_present_reservoir.setText("");
                et_inflow.setText("");
                et_river_discharge.setText("");
                et_flood_time.setText("");
                et_max_flood.setText("");
                et_conservation.setText("");

                break;

            default:
                break;

        }
    }

    @Override
    public void onSuccess(JSONObject resultJsonObject) {

        if (isFloodRiverService) {
            isFloodRiverService = false;
            riverBasineArrayList = new ArrayList<>();
            JSONArray riverBasineResultJsonArray = resultJsonObject.optJSONArray("GetRiverBasinNameResult");
            if (riverBasineResultJsonArray != null) {
                for (int i = 0; i < riverBasineResultJsonArray.length(); i++) {
                    JSONObject RiverBasineJsonObject = riverBasineResultJsonArray.optJSONObject(i);
                    RiverBasin riverBasin = new RiverBasin();
                    riverBasin.setRiverBasin(RiverBasineJsonObject.optString("RIVER_BASIN"));

                    riverBasin.setId(RiverBasineJsonObject.optString("ID"));

                    riverBasineArrayList.add(riverBasin);
                }
                populateRiverBasinDropdown();
            }
        } else if (isgetReservoirService) {
            isgetReservoirService = false;
            reservoirList = new ArrayList<String>();
            JSONArray ResultJsonArray = resultJsonObject.optJSONArray("GetReservoirNameByRiverBasinIDResult");
            if (ResultJsonArray != null) {
                for (int i = 0; i < ResultJsonArray.length(); i++) {
                    JSONObject JsonObject = ResultJsonArray.optJSONObject(i);

                    reservoirList.add(JsonObject.optString("RESERVOIR_NAME"));


                }
                populateReservoirDropdown();
            }
        } else if (isFloodLevelDataService) {
            isFloodLevelDataService = false;

            JSONObject ResultJson = resultJsonObject.optJSONObject("GetAnnualFloodDataByReservoirNameRiverBasinResult");

            et_max_flood.setText((ResultJson.optString("MAX_FLOOD_LEVEL") != "null") ? ResultJson.optString("MAX_FLOOD_LEVEL") : "Data Not available");
            et_conservation.setText((ResultJson.optString("CONV_POND_LEVEL") != "null") ? ResultJson.optString("CONV_POND_LEVEL") : "Data Not available");

            floodId = ResultJson.optString("FLOOD_ID");

        } else if (isAllFloodDataService) {
            isAllFloodDataService = false;
            JSONObject succeessObj = resultJsonObject.optJSONObject("SaveDailyFloodDataResult");
            message = succeessObj.optString("Message");
            String status = succeessObj.optString("Status");
            if (status.equals("1")) {


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                showCustomToastAlertStressful(mContext, message, R.drawable.happy_emoji);
                finish();
            } else {
                showCustomToastAlert(mContext, message, R.drawable.sorrow_emoji);
            }
        }

    }

    public void populateRiverBasinDropdown() {
        if (riverBasineArrayList.size() > 0 ) {
            dropdown_riverBasin.setEnabled(true);
            String[] corridorArray = new String[riverBasineArrayList.size()];

            for (int i = 0; i < riverBasineArrayList.size(); i++) {
                corridorArray[i] = riverBasineArrayList.get(i).getRiverBasin();
            }
            dropdown_riverBasin.setItems(corridorArray);
        }
        else {

            et_remarks.setText("");
            et_present_reservoir.setText("");
            et_inflow.setText("");
            et_river_discharge.setText("");
            et_flood_time.setText("");
            et_max_flood.setText("");
            et_conservation.setText("");
        }

        et_remarks.setText("");
        et_present_reservoir.setText("");
        et_inflow.setText("");
        et_river_discharge.setText("");
        et_flood_time.setText("");
        et_max_flood.setText("");
        et_conservation.setText("");
    }

    public void populateReservoirDropdown() {
        if (reservoirList.size() > 0 && reservoirList != null) {

            String[] corridorArray = new String[reservoirList.size()];

            for (int i = 0; i < reservoirList.size(); i++) {
                corridorArray[i] = reservoirList.get(i);
            }
            dropdown_reservoir.setItems(corridorArray);
        }
            else{

            }

        }


    @Override
    public void onError() {

    }


    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }


}

