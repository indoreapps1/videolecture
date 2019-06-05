package com.example.videolecture.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolecture.R;

public class OtpVerificationActivity extends AppCompatActivity {

    TextView txt_Phone;
    Button btn_login_conti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        btn_login_conti=findViewById(R.id.btn_login_conti);
        txt_Phone=findViewById(R.id.txt_Phone);
//        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
//        String getPhone=preferences.getString("phone", null);
        Bundle bundle=getIntent().getExtras();
        String getPhone=bundle.getString("number");
        Toast.makeText(this, ""+getPhone, Toast.LENGTH_SHORT).show();
        txt_Phone.setText("Hello "+ getPhone);
        btn_login_conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));
            }
        });
    }
}
