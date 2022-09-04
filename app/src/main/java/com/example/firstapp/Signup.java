package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Models.Semester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    EditText userName ,Email , Password, Confirmpassword;
    Button signup;
    private RequestQueue queue;
    Spinner category,semester;
    List<Semester> semesterList;
    List<String> sno;
    int semesterId =1;
    EditText cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("SignUp form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);
        queue.start();

        semester = findViewById(R.id.semester);
        cgpa = findViewById(R.id.cgpa);
        loadSemesters();


        category= (Spinner) findViewById(R.id.spincategory);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.category));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

//if you want to set any action you can do in this listener
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

                boolean b = category.getSelectedItem().toString().equals("Teacher");

                if(b){
                    semester.setVisibility(View.GONE);
                    cgpa.setVisibility(View.GONE);
                }else{
                    semester.setVisibility(View.VISIBLE);
                    cgpa.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int i, long id) {
                semesterId = semesterList.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

       userName = findViewById(R.id.uname);
        Email= findViewById(R.id.email);
       // Category = findViewById(R.id.category);
       Password= findViewById(R.id.password);
        Confirmpassword = findViewById(R.id.confrmpasswrd);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userName.getText().toString().isEmpty()) {
                    userName.setError("enter username");
                }
                  else {
                    if (isEmail(Email) == false) {
                        Email.setError("Enter valid email!");

                    } else {
                        if (Password.getText().toString().isEmpty()) {
                            Password.setError("Enter password");

                        } else {
                            if (!Password.getText().toString().equals(Confirmpassword.getText().toString())) {
                                Confirmpassword.setError("confirm Password is correct");

                            } else {
                                signup();
                            }
                        }
                    }
                }
            }
        });

    }
    private void signup()
    {
        try {


                final String username = userName.getText().toString();
                final String email = Email.getText().toString();
                final String Category=category.getSelectedItem().toString();
                final String password = Password.getText().toString();
                final String confirmpassword = Confirmpassword.getText().toString();
                final String uri = Globals.BaseUrl + "survey/usersignup";
                //Toast.makeText(login_form.this, uri, Toast.LENGTH_SHORT).show();

                JSONObject jsonData = new JSONObject();
                jsonData.put("SC_Uname", username);
                jsonData.put("SC_Email", email);
                  jsonData.put("SC_Category",Category);
                jsonData.put("SC_Password", password);

             if(category.getSelectedItem().toString().equals("Student")){
                  jsonData.put("sem_id",semesterId);
                  double stdCgpa = Double.parseDouble(cgpa.getText().toString());
                 jsonData.put("cgpa",stdCgpa);
               }

                //StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>(){
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, uri, jsonData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(Signup.this, "Success", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Intent intent=new Intent(Signup.this,createsurvey.class);
                        startActivity(intent);
                        Toast.makeText(Signup.this, "Success", Toast.LENGTH_LONG).show();
                        userName.setText("");
                        Email.setText("");
                        //Category.setText("");
                        Password.setText("");
                        Confirmpassword.setText("");


                    }
                });
                queue.add(req);

        }
        catch (Exception e)
        {
            Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    private void loadSemesters(){
        sno = new ArrayList<>();
        semesterList = new ArrayList<>();
        String url = Globals.BaseUrl+"survey/getSemesters";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    Semester s = new Semester();
                    try {
                        s.setId(response.getJSONObject(i).getInt("id"));
                        s.setSemester(response.getJSONObject(i).getString("semester"));
                        s.setDiscipline(response.getJSONObject(i).getString("discipline"));
                        s.setSection(response.getJSONObject(i).getString("section"));
                        semesterList.add(s);
                        String semester = "BS("+semesterList.get(i).getDiscipline().toUpperCase()+")-"
                                +semesterList.get(i).getSemester()+"-"+semesterList.get(i).getSection().toUpperCase();
                        sno.add(semester);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                for(int i=0;i<sno.size();i++){
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item,sno);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    semester.setAdapter(spinnerArrayAdapter);
                }
            }
        },null);

        queue.add(req);



    }

//    void checkDataEntered() {
//
//       if (!Password.getText().toString().equals(Confirmpassword.getText().toString()))
//       {
//            Confirmpassword.setError("confirm Password is correct");
//          // Toast.makeText(this,"confirm Password is correct", Toast.LENGTH_LONG).show();
//       }
//       /*else
//       {
//           Toast.makeText(this,"confirm Password is not correct", Toast.LENGTH_SHORT).show();
//       }*/
//
//
//        if (isEmail(Email) == false) {
//            Email.setError("Enter valid email!");
//        }
//
//    }

}