package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class multiple_choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);
        getSupportActionBar().setTitle("Multiple Choice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}