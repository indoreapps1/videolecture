package com.example.videolecture.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videolecture.R;

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
                mPhone = edt_phone.getText().toString();
                if (mPhone.length() != 10) {
                    edt_phone.setError("Please Enter Valid Phone");
                    edt_phone.requestFocus();
                } else {
                    SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("phone", mPhone);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("phone", mPhone);
                    startActivity(intent);
                    edt_phone.setText("");
                }
            }
        });
    }
}
