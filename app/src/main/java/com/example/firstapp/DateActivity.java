package com.example.firstapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    private Button start,end,save;
    private RequestQueue queue;
    static TextView sDate,eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        start = findViewById(R.id.startDate);
        end = findViewById(R.id.endDate);
        save = findViewById(R.id.saveDate);
        sDate = findViewById(R.id.sDate);
        eDate = findViewById(R.id.eDate);
        queue= Volley.newRequestQueue(this);
        queue.start();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment date = new DateActivity.StartDate();
                date.show(getSupportFragmentManager(), "datePicker");
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment date = new DateActivity.EndDate();
                date.show(getSupportFragmentManager(), "datePicker");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d1 = sDate.getText().toString();
                String d2 = eDate.getText().toString();
                if(!d1.isEmpty() && !d2.isEmpty()){
                    String url = Globals.BaseUrl+"survey/postDate";
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("sid",Globals.survey_id);
                        jsn.put("uid",Globals.logged_user_id);
                        jsn.put("start",d1);
                        jsn.put("end",d2);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsn,
                                new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(DateActivity.this, "Saved!..", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),createsurvey.class));
                            }
                        },null);
                        queue.add(request);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                }else{
                    Toast.makeText(DateActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class StartDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final LocalDateTime nw = LocalDateTime.now();
           // final Calendar c = Calendar.getInstance();
            int year = nw.getYear();
            int month = nw.getMonthValue();
            int day = nw.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = day+"/"+month+"/"+year;
            Log.d("DATE",date);
            sDate.setText(date);
        }

        }

    public static class EndDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final LocalDateTime nw = LocalDateTime.now();
            // final Calendar c = Calendar.getInstance();
            int year = nw.getYear();
            int month = nw.getMonthValue();
            int day = nw.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = day + "/" + month + "/" + year;
            Log.d("DATE", date);
            eDate.setText(date);
        }

    }


    }