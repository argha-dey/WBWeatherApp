package com.cyberswift.weatherapp.activity;

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

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.callback.AlertDialogCallBack;
import com.cyberswift.weatherapp.dropdown.DropDownViewForXML;
import com.cyberswift.weatherapp.model.AllRainFallDataFatch;
import com.cyberswift.weatherapp.model.District;
import com.cyberswift.weatherapp.model.RainType;
import com.cyberswift.weatherapp.model.RiverBasin;
import com.cyberswift.weatherapp.model.Station;
import com.cyberswift.weatherapp.network.ServerResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RainGaugeRecordActivity extends BaseActivity implements View.OnClickListener, ServerResponseCallback {
    private Context mContext;
    private EditText et_Rainfall_last24hrs, et_Cum_Rainfall_january_1st, et_rain_gauge_remarks, et_Cum_Rainfall_june_1st, et_Annual_Rainfall;
    public DropDownViewForXML district_dropdown, station_dropdown, riverBasin_dropdown, type_dropdown, jurisdiction_dropdown;
    private Button btn_rain_gauge_submit, btn_rain_gauge_reset;
    private String districtID, stationName, riverBasinId, rainGaugeId, message;
    protected DropDownClickListener DropDownClickListener;
    private ArrayList<District> districtArrayList;
    private ArrayList<Station> stationArrayList;
    private ArrayList<RiverBasin> riverBasineArrayList;
    private ArrayList<RainType> rainTypesArrayList;
    private ArrayList<AllRainFallDataFatch> allRainFallDataFatchesArrayList;

    private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_rain_gauge_data_records);
        //  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
        // R.layout.title);
        mContext = RainGaugeRecordActivity.this;
        init();

    }


    private void init() {

        district_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_district);
        station_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_station_name);
        riverBasin_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_river_basin_name);
        type_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_rain_type);


        et_Rainfall_last24hrs = (EditText) findViewById(R.id.et_rainfall_amount_in_24hrs);
        et_Rainfall_last24hrs.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_Annual_Rainfall = (EditText) findViewById(R.id.et_annual_rainfall);
        et_Annual_Rainfall.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_Cum_Rainfall_january_1st = (EditText) findViewById(R.id.et_cum_rainfall_1st_january);
        et_Cum_Rainfall_january_1st.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_Cum_Rainfall_june_1st = (EditText) findViewById(R.id.et_cum_rainfall_1st_june);
        et_Cum_Rainfall_june_1st.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_rain_gauge_remarks = (EditText) findViewById(R.id.et_rain_gauge_remarks);

        btn_rain_gauge_submit = (Button) findViewById(R.id.btn_rain_gauge_submit);
        btn_rain_gauge_submit.setOnClickListener(this);
        btn_rain_gauge_reset = (Button) findViewById(R.id.btn_rain_gauge_reset);
        btn_rain_gauge_reset.setOnClickListener(this);

        DistrictWebserviceCalling();

        et_rain_gauge_remarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_rain_gauge_remarks.setHint("");

                } else
                    et_rain_gauge_remarks.setHint("Enter your comments here.");

            }
        });
        district_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                districtID = districtArrayList.get(position).getDistrictID();

                //Toast.makeText(getApplicationContext(), districtID, Toast.LENGTH_LONG).show();
                stationWebserviceCalling(districtID);
                isStationService = true;


            }
        });


        station_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                stationName = stationArrayList.get(position).getRainStation();

                // Toast.makeText(getApplicationContext(), stationName, Toast.LENGTH_LONG).show();
                isRiverBasinService = true;
                riverBasinWebserviceCalling(stationName);


            }
        });
        riverBasin_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                riverBasinId = riverBasineArrayList.get(position).getId();
                Log.d("tag", "RiverBAsinId" + riverBasinId);
                isRainType = true;
                rainTypeWebserviceCalling();


            }
        });


        type_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //stationName = stationArrayList.get(position).getRainStation();
                et_rain_gauge_remarks.setText("");
                et_Rainfall_last24hrs.setText("");
                isgetAllRainfallData = true;
                Log.d("tag", "RiverBAsinId" + riverBasinId);
                Log.d("tag", "stationName" + stationName);
                rainfallDatagetWebserviceCalling(stationName, riverBasinId);


            }
        });

    }


    public void populateDistrictDropdown() {


        if (districtArrayList.size() > 0) {

            district_dropdown.setEnabled(true);

            String[] corridorArray = new String[districtArrayList.size()];


            for (int i = 0; i < districtArrayList.size(); i++) {
                corridorArray[i] = districtArrayList.get(i).getDistrictName();

            }
            district_dropdown.setItems(corridorArray);

        } else {

            et_Annual_Rainfall.setText("");
            et_Rainfall_last24hrs.setText("");
            et_Cum_Rainfall_june_1st.setText("");
            et_Cum_Rainfall_january_1st.setText("");
            et_rain_gauge_remarks.setText("");

        }
        station_dropdown.setText("");
        riverBasin_dropdown.setText("");
        type_dropdown.setText("");
        station_dropdown.setEnabled(false);
        riverBasin_dropdown.setEnabled(false);
        type_dropdown.setEnabled(false);
    }

    public void populateStationDropdown() {

        Log.d("tag", "stationArrayList.size()--" + stationArrayList.size());
        if (stationArrayList.size() > 0) {
            station_dropdown.setEnabled(true);
            String[] corridorArray = new String[stationArrayList.size()];

            for (int i = 0; i < stationArrayList.size(); i++) {
                corridorArray[i] = stationArrayList.get(i).getRainStation();
            }
            station_dropdown.setItems(corridorArray);

        }

        riverBasin_dropdown.setText("");
        type_dropdown.setText("");

        riverBasin_dropdown.setEnabled(false);
        type_dropdown.setEnabled(false);
        et_Annual_Rainfall.setText("");
        et_Rainfall_last24hrs.setText("");
        et_Cum_Rainfall_june_1st.setText("");
        et_Cum_Rainfall_january_1st.setText("");
        et_rain_gauge_remarks.setText("");
    }


    public void populateRiverBasinDropdown() {
        if (riverBasineArrayList.size() > 0 && riverBasineArrayList != null) {
            riverBasin_dropdown.setEnabled(true);
            String[] corridorArray = new String[riverBasineArrayList.size()];

            for (int i = 0; i < riverBasineArrayList.size(); i++) {
                corridorArray[i] = riverBasineArrayList.get(i).getRiverBasin();
            }
            riverBasin_dropdown.setItems(corridorArray);
        } else {
            riverBasin_dropdown.setEnabled(false);
            riverBasin_dropdown.setText("");

        }
        type_dropdown.setText("");
        type_dropdown.setEnabled(false);
        et_Annual_Rainfall.setText("");
        et_Rainfall_last24hrs.setText("");
        et_Cum_Rainfall_june_1st.setText("");
        et_Cum_Rainfall_january_1st.setText("");
        et_rain_gauge_remarks.setText("");

    }

    public void populateRainTypeDropdown() {


        if (rainTypesArrayList.size() > 0) {

            type_dropdown.setEnabled(true);

            String[] corridorArray = new String[rainTypesArrayList.size()];


            for (int i = 0; i < rainTypesArrayList.size(); i++) {
                corridorArray[i] = rainTypesArrayList.get(i).getType();

            }
            type_dropdown.setItems(corridorArray);

        }
        et_Annual_Rainfall.setText("");
        et_Rainfall_last24hrs.setText("");
        et_Cum_Rainfall_june_1st.setText("");
        et_Cum_Rainfall_january_1st.setText("");
        et_rain_gauge_remarks.setText("");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_rain_gauge_submit:
                if (district_dropdown.getText().toString().trim().isEmpty()) {

                    showCustomToastAlert(mContext, "Please Select District", R.drawable.sorrow_emoji);

                } else if (station_dropdown.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Please Select Station", R.drawable.sorrow_emoji);


                } else if (riverBasin_dropdown.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Please Select River Basin", R.drawable.sorrow_emoji);


                } else if (type_dropdown.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Please Select Type", R.drawable.sorrow_emoji);


                } else if (et_Rainfall_last24hrs.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Rainfall Last24hrs. Field is Blank", R.drawable.sorrow_emoji);


                } else if (et_Annual_Rainfall.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Annual Rainfall Field is Blank ", R.drawable.sorrow_emoji);

                } else if (et_Cum_Rainfall_january_1st.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Cum  Rainfall 1st January. Field is Blank", R.drawable.sorrow_emoji);


                } else if (et_Cum_Rainfall_june_1st.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Cum  Rainfall 1st june. Field is Blank", R.drawable.sorrow_emoji);


                } else {
                    /*Util.showCallBackMessageWithOkCancel(mContext, "Do you want to save data?", new AlertDialogCallBack() {
                        @Override
                        public void onSubmit() {
                            submitRainfallWebserviceCalling(rainGaugeId, et_Rainfall_last24hrs.getText().toString().trim(), et_rain_gauge_remarks.getText().toString().trim());

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
*/
                    showDialog(RainGaugeRecordActivity.this, "Do you want to save data?", new AlertDialogCallBack() {
                        @Override
                        public void onSubmit() {
                            submitRainfallWebserviceCalling(rainGaugeId, et_Rainfall_last24hrs.getText().toString().trim(), et_rain_gauge_remarks.getText().toString().trim());


                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                }

                break;


            case R.id.btn_rain_gauge_reset:
                district_dropdown.setText("");
                station_dropdown.setText("");
                riverBasin_dropdown.setText("");
                type_dropdown.setText("");
                et_Annual_Rainfall.setText("");
                et_Rainfall_last24hrs.setText("");
                et_Cum_Rainfall_june_1st.setText("");
                et_Cum_Rainfall_january_1st.setText("");
                et_rain_gauge_remarks.setText("");

                break;

            default:
                break;

        }
    }

    @Override
    public void onSuccess(JSONObject resultJsonObject) {
        if (isDistrictService) {
            districtArrayList = new ArrayList<>();
            isDistrictService = false;

            JSONArray districtResultJsonArray = resultJsonObject.optJSONArray("GetDistrictNameResult");
            if (districtResultJsonArray != null) {
                for (int i = 0; i < districtResultJsonArray.length(); i++) {
                    JSONObject DistrictJsonObject = districtResultJsonArray.optJSONObject(i);
                    District district = new District();
                    district.setDistrictName(DistrictJsonObject.optString("DISTRICT"));

                    district.setDistrictID(DistrictJsonObject.optString("DISTRICT_ID"));

                    districtArrayList.add(district);
                }
            }

            populateDistrictDropdown();
        } else if (isStationService) {
            stationArrayList = new ArrayList<>();

            isStationService = false;
            // isStationService=false;
            JSONArray stationResultJsonArray = resultJsonObject.optJSONArray("GetRainGaugeStationResult");
            if (stationResultJsonArray != null) {
                for (int i = 0; i < stationResultJsonArray.length(); i++) {
                    JSONObject StationJsonObject = stationResultJsonArray.optJSONObject(i);
                    Station station = new Station();
                    station.setRainStation(StationJsonObject.optString("RAIN_STATION"));

                    station.setId(StationJsonObject.optString("ID"));

                    stationArrayList.add(station);
                }
            }

            populateStationDropdown();
        } else if (isRiverBasinService) {
            isRiverBasinService = false;
            riverBasineArrayList = new ArrayList<>();
            JSONArray riverBasineResultJsonArray = resultJsonObject.optJSONArray("GetRiverBasinResult");
            if (riverBasineResultJsonArray != null) {
                for (int i = 0; i < riverBasineResultJsonArray.length(); i++) {
                    JSONObject RiverBasineJsonObject = riverBasineResultJsonArray.optJSONObject(i);
                    RiverBasin riverBasin = new RiverBasin();
                    riverBasin.setRiverBasin(RiverBasineJsonObject.optString("RIVER_BASIN"));

                    riverBasin.setId(RiverBasineJsonObject.optString("ID"));

                    riverBasineArrayList.add(riverBasin);
                }
            }

            populateRiverBasinDropdown();
        } else if (isRainType) {
            isRainType = false;
            rainTypesArrayList = new ArrayList<>();
            JSONArray rainTypeResultJsonArray = resultJsonObject.optJSONArray("GetRiverTypeResult");
            if (rainTypeResultJsonArray != null) {
                for (int i = 0; i < rainTypeResultJsonArray.length(); i++) {
                    JSONObject rainTypeJsonObject = rainTypeResultJsonArray.optJSONObject(i);
                    RainType rainType = new RainType();
                    rainType.setType(rainTypeJsonObject.optString("TYPE"));
                    rainType.setTypeId(rainTypeJsonObject.optString("ID"));


                    rainTypesArrayList.add(rainType);
                }
            }

            populateRainTypeDropdown();
        } else if (isgetAllRainfallData) {
            isgetAllRainfallData = false;

            JSONObject allRainfallDataFatchResultJson = resultJsonObject.optJSONObject("GetAnnualRainDataResult");

            et_Annual_Rainfall.setText((allRainfallDataFatchResultJson.optString("ANNUAL_RAINFALL") != "null") ? allRainfallDataFatchResultJson.optString("ANNUAL_RAINFALL") : "Data Not available");
            et_Cum_Rainfall_january_1st.setText((allRainfallDataFatchResultJson.optString("JAN_CUM_RAINFALL") != "null") ? allRainfallDataFatchResultJson.optString("JAN_CUM_RAINFALL") : "Data Not available");
            et_Cum_Rainfall_june_1st.setText((allRainfallDataFatchResultJson.optString("JUN_CUM_RAINFALL") != "null") ? allRainfallDataFatchResultJson.optString("JUN_CUM_RAINFALL") : "Data Not available");
            rainGaugeId = allRainfallDataFatchResultJson.optString("RAIN_GAUGE_ID");

        } else if (isFinalSubmit) {
            isFinalSubmit = false;
            JSONObject succeessObj = resultJsonObject.optJSONObject("SaveDailyRainGaugeDataResult");
            message = succeessObj.optString("Message");
            String status = succeessObj.optString("Status");
            if (status.equals("1")) {
                showCustomToastAlertStressful(mContext, message, R.drawable.happy_emoji);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                finish();
            } else {

                showCustomToastAlert(mContext, message, R.drawable.sorrow_emoji);
            }
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