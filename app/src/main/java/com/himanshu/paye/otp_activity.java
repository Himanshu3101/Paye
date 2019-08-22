package com.himanshu.paye;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.himanshu.paye.Dashboard.Dashboard_Activity;
import com.mukesh.OtpView;

public class otp_activity extends AppCompatActivity implements View.OnClickListener {
    private OtpView otpView;
    AppCompatButton otpSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.otp_layout);
        initialize();
        otpSubmit.setOnClickListener(this);
    }

    public void initialize() {
        otpView = findViewById(R.id.otp_view);
        otpSubmit = findViewById(R.id.otpSubmit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otpSubmit:
                String otp = otpView.getText().toString();
                if(otp.equals("1234")){
                    Intent intent = new Intent(getApplicationContext(), Dashboard_Activity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Please input valid OTP.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
