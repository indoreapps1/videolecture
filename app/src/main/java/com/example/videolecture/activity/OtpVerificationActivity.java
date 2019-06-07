package com.example.videolecture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        txt_Phone.setText(""+ phone);
        btn_login_conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));
                if (Validation()){
                    if (Utility.isOnline(OtpVerificationActivity.this)){
                        final ProgressDialog dialog=new ProgressDialog(OtpVerificationActivity.this);
                        dialog.setMessage("Loading...");
                        dialog.show();
                        ServiceCaller serviceCaller=new ServiceCaller(OtpVerificationActivity.this);
                        serviceCaller.callOtpData(phone, otp, new IAsyncWorkCompletedCallback() {
                            @Override
                            public void onDone(String workName, boolean isComplete) {
                                dialog.dismiss();
                                if (isComplete){
                                    if (!workName.equalsIgnoreCase("invalid otp")){
                                        Toast.makeText(OtpVerificationActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(OtpVerificationActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(OtpVerificationActivity.this, "Invalid otp", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                else {
                                    Toast.makeText(OtpVerificationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(OtpVerificationActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private boolean Validation(){
        otp=edt_otp.getText().toString();
        if (otp.length()==0){
            edt_otp.setError("Enter Otp");
            edt_otp.requestFocus();
            return false;
        }
        else if (otp.length()!=4){
            edt_otp.setError("Enter Valid Otp");
            edt_otp.requestFocus();
            return false;
        }
        return true;
    }
}
