package com.example.videolecture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolecture.R;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.MyPojo;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.Utility;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

public class OtpVerificationActivity extends AppCompatActivity {

    TextView txt_Phone;
    EditText edt_otp;
    Button btn_login_conti;
    String phone, otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        btn_login_conti = findViewById(R.id.btn_login_conti);
        txt_Phone = findViewById(R.id.txt_Phone);
        edt_otp = findViewById(R.id.edt_otp);
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        txt_Phone.setText(phone);
        btn_login_conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    if (Utility.isOnline(OtpVerificationActivity.this)) {
                        final ProgressDialog dialog = new ProgressDialog(OtpVerificationActivity.this);
                        dialog.setMessage("Verify Otp Wait.....");
                        dialog.show();
                        ServiceCaller serviceCaller = new ServiceCaller(OtpVerificationActivity.this);
                        serviceCaller.callOtpData(phone, otp, new IAsyncWorkCompletedCallback() {
                            @Override
                            public void onDone(String workName, boolean isComplete) {
                                dialog.dismiss();
                                if (isComplete) {
                                    if (!workName.equalsIgnoreCase("invalid otp")) {
                                        MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                                        for (Result result : myPojo.getResult()) {
                                            SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putInt("id", result.getLoginId());
                                            editor.apply();
                                            Toasty.success(OtpVerificationActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(OtpVerificationActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        edt_otp.setError("Invalid otp");
                                        edt_otp.requestFocus();

                                    }
                                } else {
                                    Toasty.error(OtpVerificationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    } else {
                        Toasty.info(OtpVerificationActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private boolean Validation() {
        otp = edt_otp.getText().toString();
        if (otp.length() == 0) {
            edt_otp.setError("Enter Otp");
            edt_otp.requestFocus();
            return false;
        } else if (otp.length() != 4) {
            edt_otp.setError("Enter Valid Otp");
            edt_otp.requestFocus();
            return false;
        }
        return true;
    }
    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
