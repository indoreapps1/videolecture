package com.example.videolecture.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videolecture.R;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.utilities.Utility;

public class LoginActivity extends AppCompatActivity {

    Button btn_login_next;
    EditText edt_phone;
    String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        edt_phone = findViewById(R.id.edt_phone);
        btn_login_next = findViewById(R.id.btn_login_next);
        btn_login_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    if (Utility.isOnline(LoginActivity.this)) {
                        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Loading...");
                        dialog.show();
                        ServiceCaller serviceCaller = new ServiceCaller(LoginActivity.this);
                        serviceCaller.callLoginData(mPhone, new IAsyncWorkCompletedCallback() {
                            @Override
                            public void onDone(String workName, boolean isComplete) {
                                dialog.dismiss();
                                if (isComplete) {
                                    Toast.makeText(LoginActivity.this, "Otp Send Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                                    intent.putExtra("phone", mPhone);
                                    startActivity(intent);
                                    edt_phone.setText("");
                                    edt_phone.requestFocus();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Otp does not send", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

//                else {
//                    SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("phone", mPhone);
//                    editor.apply();
//                    Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
//                    intent.putExtra("phone", mPhone);
//                    startActivity(intent);
//                    edt_phone.setText("");
//                }

            }
        });
    }

    private boolean Validation() {
        mPhone = edt_phone.getText().toString();
        if (mPhone.length() == 0) {
            edt_phone.setError("Please Enter Phone Number");
            edt_phone.requestFocus();
            return false;
        } else if (mPhone.length() != 10) {
            edt_phone.setError("Please Enter Valid Phone");
            edt_phone.requestFocus();
            return false;
        }
        return true;
    }
}
