package com.himanshu.paye.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.himanshu.paye.R;
import com.himanshu.paye.otp_activity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    LinearLayout linearLayoutSignIn, linearLayoutForgotPassword;
    AppCompatTextView textViewSignIn, textViewForgotPassword;
    String signInCLicked = "1";
    private boolean mIsBackVisible = false;
    AppCompatButton buttonLogin;
    TextInputEditText editTextUserPin, editTextEmailId;
    Activity activity;
    TextInputLayout userEmailLayout,userIdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        activity = new Activity();
        initialize();

        overridePendingTransition(0, 0);

        textViewSignIn.setText(Html.fromHtml("<u>" + getString(R.string.sign_in) + "</u>"));
        textViewSignIn.setPaintFlags(textViewSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //getSupportActionBar().hide();
        loadAnimations();
        changeCameraDistance(linearLayoutSignIn, linearLayoutForgotPassword);

        linearLayoutForgotPassword.setAlpha(0);
        linearLayoutForgotPassword.setVisibility(View.GONE);

        textViewSignIn.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    private void changeCameraDistance(View linearLayoutSignIn, View linearLayoutForgotPassword) {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        linearLayoutSignIn.setCameraDistance(scale);
        linearLayoutForgotPassword.setCameraDistance(scale);
    }

    private void loadAnimations() {

        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetRightOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);

    }

    public void initialize() {
        linearLayoutSignIn = findViewById(R.id.linearLayoutSignIn);
        linearLayoutForgotPassword = findViewById(R.id.linearLayoutForgotPassword);
        textViewSignIn = findViewById(R.id.textViewSignIn);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextUserPin = findViewById(R.id.editTextUserPin);
        editTextEmailId = findViewById(R.id.editTextEmailId);

        userEmailLayout = findViewById(R.id.userEmailLayout);
        userIdLayout = findViewById(R.id.userIdLayout);

        buttonLogin.requestFocus();
    }

    public void flipCard() {

        if (!mIsBackVisible) {
            linearLayoutForgotPassword.setVisibility(View.VISIBLE);
            mSetRightOut.setTarget(linearLayoutSignIn);
            mSetLeftIn.setTarget(linearLayoutForgotPassword);
            mSetRightOut.start();
            mSetLeftIn.start();
            editTextEmailId.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextEmailId, InputMethodManager.SHOW_IMPLICIT);
            userEmailLayout.setVisibility(View.VISIBLE);
            userIdLayout.setVisibility(View.GONE);
            mIsBackVisible = true;
        } else {
            linearLayoutSignIn.setVisibility(View.VISIBLE);
            mSetRightOut.setTarget(linearLayoutForgotPassword);
            mSetLeftIn.setTarget(linearLayoutSignIn);
            mSetRightOut.start();
            mSetLeftIn.start();
            editTextUserPin.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextUserPin, InputMethodManager.SHOW_IMPLICIT);
            userEmailLayout.setVisibility(View.GONE);
            userIdLayout.setVisibility(View.VISIBLE);
            mIsBackVisible = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewSignIn:
                onSignInClick();
                break;
            case R.id.textViewForgotPassword:
                onForgotPasswordInClick();
                break;
            case R.id.buttonLogin:
                if(isConnected()){
                   String userId = editTextUserPin.getText().toString();
                   if(userId.equals("9910609284")){
//                       SnackBar(v,"Login Successfully.");
                       Toast.makeText(getApplicationContext(), "Login Successfully.", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(getApplicationContext(), otp_activity.class);
                       startActivity(intent);
                       finish();
                   }else{
                       Toast.makeText(getApplicationContext(), "Please input valid phone number.", Toast.LENGTH_SHORT).show();
//                       SnackBar(v,"Please input valid phone number.");
                   }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No internet available!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void onSignInClick() {

        if (!signInCLicked.equals("1")) {
            textViewForgotPassword.setText(getString(R.string.forgotPassword));
            textViewForgotPassword.setPaintFlags(0);
            textViewSignIn.setText(Html.fromHtml("<u>" + getString(R.string.sign_in) + "</u>"));
            textViewSignIn.setPaintFlags(textViewSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textViewSignIn.setTextColor(getResources().getColor(R.color.white));
            textViewForgotPassword.setTextColor(getResources().getColor(R.color.white));
            flipCard();
            signInCLicked = "1";
        }
    }

    public void onForgotPasswordInClick() {
        if (signInCLicked.equals("1")) {

            textViewSignIn.setText(getString(R.string.sign_in));
            textViewSignIn.setPaintFlags(0);
            textViewForgotPassword.setText(Html.fromHtml("<u>" + getString(R.string.forgotPassword) + "</u>"));
            textViewForgotPassword.setPaintFlags(textViewForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textViewSignIn.setTextColor(getResources().getColor(R.color.white));
            textViewForgotPassword.setTextColor(getResources().getColor(R.color.white));
            flipCard();
            signInCLicked = "";
        }
    }

    boolean isConnected() {
        ConnectivityManager cm =(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void SnackBar(View v, String msg){
        Snackbar snackbar;
        snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.white));
        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.button_color));
        snackbar.show();
    }



}
