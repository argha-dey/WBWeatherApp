package com.cyberswift.weatherapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cyberswift.weatherapp.R;
import com.cyberswift.weatherapp.callback.AlertDialogCallBack;
import com.cyberswift.weatherapp.model.UserClass;
import com.cyberswift.weatherapp.network.ServerResponseCallback;
import com.cyberswift.weatherapp.network.VolleyTaskManager;
import com.cyberswift.weatherapp.util.Util;

import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends BaseActivity implements ServerResponseCallback {
    private Button btn_sign_in;
    private Context mContext;
    private CheckBox checkBox;
    private EditText et_userName, et_passWord;
    private UserClass user = new UserClass();
    private VolleyTaskManager volleyTaskManager;
    boolean isLoginService = false;
    private String deviceId = null;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(android.R.style.Animation_Toast);
        mContext = LoginActivity.this;
        initView();

    }

    public void initView() {
        et_passWord = (EditText) findViewById(R.id.et_password);
        et_userName = (EditText) findViewById(R.id.et_username);
        checkBox = (CheckBox) findViewById(R.id.chkbx_rememberMe);

        et_passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_passWord.setHint("");

                } else
                    et_passWord.setHint("password");

            }
        });

        if (Util.fetchUserClass(LoginActivity.this) != null) {

            user = Util.fetchUserClass(LoginActivity.this);
        }

        if (user.getIsRemember()) {
            et_userName.setText(user.getUserID());
            et_passWord.setText(user.getPassword());
            checkBox.setChecked(user.getIsRemember());
        }
        volleyTaskManager = new VolleyTaskManager(mContext);
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
    }


    public void onSignInClicked(View v) {

        if (et_userName.getText().toString().trim().isEmpty() && et_passWord.getText().toString().trim().isEmpty()) {

            showCustomToastAlert(mContext, "Please Enter UserName and Password", R.drawable.sorrow_emoji);
        } else if (et_userName.getText().toString().trim().isEmpty()) {

            showCustomToastAlert(mContext, "Please Enter a UserName", R.drawable.sorrow_emoji);
        } else if (et_passWord.getText().toString().trim().isEmpty()) {
            showCustomToastAlert(mContext, "Please Enter a Password", R.drawable.sorrow_emoji);


        } else {


            if (Util.isInternetPresent(mContext)) {

                mProgressDialog.show();
                loginWebServiceCalling();

                // login Service calling Method();
            } else
                showCustomToastAlert(mContext, "Please check your internet connection.", R.drawable.sorrow_emoji);


        }

    }

    public void onCancelClicked(View v){
        Util.showCallBackMessageWithOkCancel(mContext,"Are you sure, to End your Application ?", new AlertDialogCallBack() {
            @Override
            public void onSubmit() {
                showCustomToastAlert(mContext, "Now Your Application is End Successfully...", R.drawable.happy_emoji);
                finish();
                System.exit(0);

            }

            @Override
            public void onCancel() {

            }
        });

    }

    public void loginWebServiceCalling() {

        HashMap<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("User_Id", et_userName.getText().toString());
        paramsMap.put("Password", et_passWord.getText().toString());

        isLoginService = true;
        volleyTaskManager.doLogin(paramsMap);

    }

    @Override
    public void onSuccess(JSONObject resultJsonObject) {


        if (isLoginService) {
            JSONObject jsonObject = resultJsonObject.optJSONObject("MobileUserLogInResult");
            String status = jsonObject.optString("Status");

            if (status.equals("1")) {


                user.setUserID(jsonObject.optString("User_Id"));
                user.setPassword(et_passWord.getText().toString());
                user.setIsRemember(checkBox.isChecked());
                Util.saveUserClass(LoginActivity.this, user);


                //showCustomToastAlert(mContext, "" + jsonObject.optString("Message"), R.drawable.happy_emoji);
                openMainActivity();


            }
            else {
                mProgressDialog.dismiss();
                showCustomToastAlert(mContext, "Login Fail! Please Try Again", R.drawable.sorrow_emoji);
            }


        }

    }
    @Override
    public void onError() {

        mProgressDialog.dismiss();
        showCustomToastAlert(mContext, "Login Fail! Please Try Again", R.drawable.sorrow_emoji);

    }


    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        finish();
    }

}
