package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Admin_login extends AppCompatActivity {
    Button Adminlogin;
    EditText Aname,Apassword;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Admin Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);
        queue.start();
        Aname=findViewById(R.id.admin_name);
        Apassword=findViewById(R.id.admin_pass);


        Adminlogin=findViewById(R.id.admin_login);
        Adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {

        final String username = Aname.getText().toString();
        final String password = Apassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            Aname.setError("Please enter your username");
            Aname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Apassword.setError("Please enter your password");
            Apassword.requestFocus();
            return;
        }

        String uri=Globals.BaseUrl+"survey/adminlogin?adminname="+username+
                "&adminpassword="+password;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",""+response);
                        try {
                                Intent intent = new Intent(Admin_login.this,AdminNotificationForm.class);
                                startActivity(intent);
                            }catch (Exception ex){
                            Log.d("EX:",ex.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Admin_login.this, "Invalid name or Password", Toast.LENGTH_LONG).show();
                         Aname.setText("");
                         Apassword.setText("");
                    }
                });

        queue.add(stringRequest);

    }

}