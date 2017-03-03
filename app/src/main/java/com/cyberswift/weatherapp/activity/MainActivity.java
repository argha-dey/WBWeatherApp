package com.cyberswift.weatherapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.callback.AlertDialogCallBack;
import com.cyberswift.weatherapp.util.Util;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton btn_options;
    private PopupWindow popupWindow;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        init();
    }

    private void init() {
        Button bt_rainGauge_records = (Button) findViewById(R.id.bt_daily_rain_gauge_information);
        Button bt_riverGauge_records = (Button) findViewById(R.id.bt_daily_river_gauge_information);
        Button bt_daily_flood_report = (Button) findViewById(R.id.bt_daily_flood_report);

        bt_rainGauge_records.setOnClickListener(this);
        bt_riverGauge_records.setOnClickListener(this);
        bt_daily_flood_report.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
    }


    @Override   // cancle backPressButton
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showCustomToastAlert(mContext, "Please Logout First", R.drawable.red_warning);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_daily_rain_gauge_information:

                Intent damRecordIntent = new Intent(getApplicationContext(),RainGaugeRecordActivity.class);
                startActivity(damRecordIntent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;

            case R.id.bt_daily_river_gauge_information:

                Intent landSlideRecordIntent = new Intent(getApplicationContext(), RiverGaugeRecordsActivity.class);
                startActivity(landSlideRecordIntent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;


            case R.id.bt_daily_flood_report:

                Intent i = new Intent(getApplicationContext(), DailyFloodReportActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;

            default:
                break;
        }
    }





    public void clickOnOptionButton(View view) {
        showPopup(view);

    }

    private void showPopup(View v) {

        if (popupWindow == null || !popupWindow.isShowing()) {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.menu_option, null);
            popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(false);
            TextView tv_logout = (TextView) popupView.findViewById(R.id.tv_logout);
            TextView tv_cancel = (TextView) popupView.findViewById(R.id.tv_cancel);
            TextView tv_reload = (TextView) popupView.findViewById(R.id.tv_change_password);
            tv_cancel.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                 popupWindow.dismiss();
                                             }
                                         }
            );


            tv_logout.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    Util.showCallBackMessageWithOkCancel(popupView.getContext(), "Are You Sure?", new AlertDialogCallBack() {

                        @Override
                        public void onSubmit() {


                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                           // showCustomToastAlert(mContext, "You Have Logout Successfully...", R.drawable.happy_emoji);
                            finish();

                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                }


            });


            popupWindow.setOutsideTouchable(false);
            popupWindow.showAsDropDown(v, 100, 0);
        } else {
            //popupWindow.dismiss();
        }

    }


}

