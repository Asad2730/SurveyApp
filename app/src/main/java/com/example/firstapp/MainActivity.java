package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void btn_signupform(View view) {
        startActivity(new Intent(getApplicationContext(),Signup.class));
    }
    public void btn_adminlogin(View view) {

        startActivity(new Intent(getApplicationContext(),Admin_login.class));
    }

    public void btn_loginfrom(View view) {

        startActivity(new Intent(getApplicationContext(),login_form.class));
    }
}