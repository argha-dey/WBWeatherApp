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
import com.cyberswift.weatherapp.model.District;
import com.cyberswift.weatherapp.model.RiverBasin;
import com.cyberswift.weatherapp.model.Station;
import com.cyberswift.weatherapp.model.Trend;
import com.cyberswift.weatherapp.network.ServerResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RiverGaugeRecordsActivity extends BaseActivity implements View.OnClickListener, ServerResponseCallback {

    private EditText et_level, et_dl, et_edl, et_remarks;
    private DropDownViewForXML river_gauge_Dist_dropdown, river_gauge_station_dropdown, river_gauge_name_of_river_dropdown, river_gauge_div_jurisdiction_dropdown, tend_dropdown;
    private Button btn_river_gauge_submit, btn_river_gauge_reset;
    private String districtID, gaugeId, riverID, riverGaugeId, message,TreadID;
    private Context mContext;
    private ArrayList<District> districtArrayList;
    private ArrayList<Station> stationArrayList;
    private ArrayList<RiverBasin> riverBasineArrayList;
    private ArrayList<Trend> trendArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_gauge_records);
        mContext = RiverGaugeRecordsActivity.this;
        init();
    }


    public void init() {

        river_gauge_Dist_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_rainGauge_district);
        river_gauge_station_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_rainGauge_station);
        river_gauge_name_of_river_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_rainGauge_RiverName);

        tend_dropdown = (DropDownViewForXML) findViewById(R.id.dropdown_rainGauge_Trend);

        et_level = (EditText) findViewById(R.id.et_river_gauge_level);
        et_level.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_dl = (EditText) findViewById(R.id.et_river_DL);
        et_dl.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_edl = (EditText) findViewById(R.id.et_river_EDL);
        et_edl.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        et_remarks = (EditText) findViewById(R.id.et_river_gauge_remarks);

        btn_river_gauge_submit = (Button) findViewById(R.id.bt_daily_river_gauge_submit);
        btn_river_gauge_submit.setOnClickListener(this);
        btn_river_gauge_reset = (Button) findViewById(R.id.bt_daily_river_gauge_reset);
        btn_river_gauge_reset.setOnClickListener(this);

        riverDistrictWebserviceCalling();

        river_gauge_Dist_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                districtID = districtArrayList.get(position).getDistrictID();

                RiverStationWebserviceCalling(districtID);


            }
        });

        river_gauge_station_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                gaugeId = stationArrayList.get(position).getId();
                Log.d("tag", "RiverNameId" + gaugeId);

                riverNameWebserviceCalling(gaugeId);


            }
        });
        river_gauge_name_of_river_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.print("RiverWebServiceCalling...........");
                riverID = riverBasineArrayList.get(position).getId();
                riverTrendWebserviceCalling();
                System.out.print("RiverWebServiceCalling..... End......");


            }
        });

        tend_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                et_level.setText("");
                et_remarks.setText("");
                 TreadID = trendArrayList.get(position).tendId;
                Log.d("tag", "RiverNameId" + riverID);

                getriverGaugeDataWebserviceCalling(gaugeId, riverID);


            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_daily_river_gauge_submit:
                if (river_gauge_Dist_dropdown.getText().toString().trim().isEmpty()) {

                    showCustomToastAlert(mContext, "Please Select District", R.drawable.sorrow_emoji);

                } else if (river_gauge_station_dropdown.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "Please Select Station", R.drawable.sorrow_emoji);
                } else if (river_gauge_name_of_river_dropdown.getText().toString().trim().isEmpty()) {


                    showCustomToastAlert(mContext, "Please Select River", R.drawable.sorrow_emoji);
                } else if (tend_dropdown.getText().toString().trim().isEmpty()) {

                    showCustomToastAlert(mContext, "Please Select Tend", R.drawable.sorrow_emoji);

                } else if (et_level.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, " Level  Field is Blank ", R.drawable.sorrow_emoji);


                } else if (et_dl.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "DL Field is Blank ", R.drawable.sorrow_emoji);


                } else if (et_edl.getText().toString().trim().isEmpty()) {
                    showCustomToastAlert(mContext, "EDL  Field is Blank", R.drawable.sorrow_emoji);

                } else {
                   /* Util.showCallBackMessageWithOkCancel(mContext, "Do you want to save data?", new AlertDialogCallBack() {
                        @Override
                        public void onSubmit() {
                            submitRainfallWebserviceCalling(riverGaugeId, TreadID, et_level.getText().toString().trim(), et_remarks.getText().toString().trim());


                        }

                        @Override
                        public void onCancel() {

                        }
                    });*/

                    showDialog(RiverGaugeRecordsActivity.this, "Do you want to save data?", new AlertDialogCallBack() {
                        @Override
                        public void onSubmit() {
                            submitRainfallWebserviceCalling(riverGaugeId, TreadID, et_level.getText().toString().trim(), et_remarks.getText().toString().trim());

                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                }


                break;


            case R.id.bt_daily_river_gauge_reset:
                river_gauge_Dist_dropdown.setText("");
                river_gauge_station_dropdown.setText("");
                river_gauge_name_of_river_dropdown.setText("");
                tend_dropdown.setText("");
                et_level.setText("");
                et_dl.setText("");
                et_edl.setText("");
                et_remarks.setText("");

                break;

            default:
                break;

        }
    }

    public void populateDistrictDropdown() {


        if (districtArrayList.size() > 0) {

            river_gauge_Dist_dropdown.setEnabled(true);

            String[] corridorArray = new String[districtArrayList.size()];


            for (int i = 0; i < districtArrayList.size(); i++) {
                corridorArray[i] = districtArrayList.get(i).getDistrictName();

            }
            river_gauge_Dist_dropdown.setItems(corridorArray);

        } else {

            et_level.setText("");
            et_dl.setText("");
            et_edl.setText("");
            et_remarks.setText("");


        }

        river_gauge_station_dropdown.setText("");
        river_gauge_name_of_river_dropdown.setText("");
        tend_dropdown.setText("");
        et_level.setText("");
        et_dl.setText("");
        et_edl.setText("");
        et_remarks.setText("");
    }


    public void populateStationDropdown() {


        if (stationArrayList.size() > 0) {
            river_gauge_station_dropdown.setEnabled(true);
            String[] corridorArray = new String[stationArrayList.size()];

            for (int i = 0; i < stationArrayList.size(); i++) {
                corridorArray[i] = stationArrayList.get(i).getRainStation();
            }
            river_gauge_station_dropdown.setItems(corridorArray);

        }

        river_gauge_name_of_river_dropdown.setText("");
        tend_dropdown.setText("");
        river_gauge_name_of_river_dropdown.setEnabled(false);
        tend_dropdown.setEnabled(false);

        et_level.setText("");
        et_dl.setText("");
        et_edl.setText("");
        et_remarks.setText("");
    }

    public void populateRiverNameDropdown() {
        if (riverBasineArrayList.size() > 0) {
            river_gauge_name_of_river_dropdown.setEnabled(true);
            String[] corridorArray = new String[riverBasineArrayList.size()];

            for (int i = 0; i < riverBasineArrayList.size(); i++) {
                corridorArray[i] = riverBasineArrayList.get(i).getRiverBasin();
            }
            river_gauge_name_of_river_dropdown.setItems(corridorArray);
        }
        tend_dropdown.setText("");
        tend_dropdown.setEnabled(false);
        et_level.setText("");
        et_dl.setText("");
        et_edl.setText("");
        et_remarks.setText("");

    }

    public void populateTrendDropdown() {


        if (trendArrayList.size() > 0) {

            tend_dropdown.setEnabled(true);

            String[] corridorArray = new String[trendArrayList.size()];


            for (int i = 0; i < trendArrayList.size(); i++) {
                corridorArray[i] = trendArrayList.get(i).getTendName();

            }
            tend_dropdown.setItems(corridorArray);

        }
        et_level.setText("");
        et_dl.setText("");
        et_edl.setText("");
        et_remarks.setText("");

    }

    @Override
    public void onSuccess(JSONObject resultJsonObject) {
        if (isRiverDistrictService) {
            districtArrayList = new ArrayList<>();
            isRiverDistrictService = false;

            JSONArray districtResultJsonArray = resultJsonObject.optJSONArray("GetRiverDistrictNameResult");
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
        } else if (isRiverStationService) {
            stationArrayList = new ArrayList<>();

            isRiverStationService = false;
            JSONArray stationResultJsonArray = resultJsonObject.optJSONArray("GetRiverGaugeStationResult");
            if (stationResultJsonArray != null) {
                for (int i = 0; i < stationResultJsonArray.length(); i++) {
                    JSONObject StationJsonObject = stationResultJsonArray.optJSONObject(i);
                    Station station = new Station();
                    station.setRainStation(StationJsonObject.optString("GAUGE_STATION"));

                    station.setId(StationJsonObject.optString("GAUGE_ID"));

                    stationArrayList.add(station);
                }
            }

            populateStationDropdown();
        } else if (isRiverNameService) {
            isRiverNameService = false;
            riverBasineArrayList = new ArrayList<>();
            JSONArray riverBasineResultJsonArray = resultJsonObject.optJSONArray("GetRiverByGaugeStationResult");
            if (riverBasineResultJsonArray != null) {
                for (int i = 0; i < riverBasineResultJsonArray.length(); i++) {
                    JSONObject RiverBasineJsonObject = riverBasineResultJsonArray.optJSONObject(i);
                    RiverBasin riverBasin = new RiverBasin();
                    riverBasin.setRiverBasin(RiverBasineJsonObject.optString("RIVER"));

                    riverBasin.setId(RiverBasineJsonObject.optString("RIVER_ID"));

                    riverBasineArrayList.add(riverBasin);
                }
            }

            populateRiverNameDropdown();
        } else if (isRiverTrend) {
            isRiverTrend = false;
            trendArrayList = new ArrayList<>();
            JSONArray rainTypeResultJsonArray = resultJsonObject.optJSONArray("GetTrendResult");
            if (rainTypeResultJsonArray != null) {
                for (int i = 0; i < rainTypeResultJsonArray.length(); i++) {
                    JSONObject rainTypeJsonObject = rainTypeResultJsonArray.optJSONObject(i);
                    Trend riverTrade = new Trend();
                    riverTrade.setTendName(rainTypeJsonObject.optString("TREND_NAME"));
                    riverTrade.setTendId(rainTypeJsonObject.optString("ID"));

                    riverTrade.setTendShortForm(rainTypeJsonObject.optString("TREND_SHORT_FORM"));

                    trendArrayList.add(riverTrade);
                }
            }

            populateTrendDropdown();
        } else if (getAllRivergaugeData) {
            getAllRivergaugeData = false;

            JSONObject allRiverDataFatchResultJson = resultJsonObject.optJSONObject("GetAnnualRiverDataResult");

            et_dl.setText((allRiverDataFatchResultJson.optString("DANGER_LEVEL") != "null") ? allRiverDataFatchResultJson.optString("DANGER_LEVEL") : "Data Not available");
            et_edl.setText((allRiverDataFatchResultJson.optString("EXTREME_DANGER_LEVEL") != "null") ? allRiverDataFatchResultJson.optString("EXTREME_DANGER_LEVEL") : "Data Not available");

            riverGaugeId = allRiverDataFatchResultJson.optString("RIVER_GAUGE_ID");

        } else if (isRiverFinalSubmit) {
            isRiverFinalSubmit = false;
            JSONObject succeessObj = resultJsonObject.optJSONObject("SaveDailyRiverGaugeDataResult");
            message = succeessObj.optString("Message");
            String status = succeessObj.optString("Status");
            if (status.equals("1")) {

                showCustomToastAlertStressful(mContext, message, R.drawable.happy_emoji);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                startActivity(intent);
                finish();
            } else {

                showCustomToastAlert(mContext, message, R.drawable.red_warning);
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