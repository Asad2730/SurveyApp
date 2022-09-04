package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class login_form extends AppCompatActivity {
    Button login;
    EditText name,password;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);
        queue.start();

        name=findViewById(R.id.name);
        password=findViewById(R.id.pass);


        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login(){
        try {
            final String username = name.getText().toString();
            final String userPassword = password.getText().toString();

            //validating inputs
            if (TextUtils.isEmpty(username)) {
                name.setError("Please enter your username");
                name.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(userPassword)) {
                password.setError("Please enter your password");
                password.requestFocus();
                return;
            }

            String uri=Globals.BaseUrl+"survey/userlogin"+"?username="+username+"&password="+userPassword;
            StringRequest req=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        int sid = Integer.parseInt(obj.get("SC_ID").toString());
                        String category = obj.get("SC_Category").toString();
                        Globals.logged_user_id = sid;
                        Globals.logged_userCategory = category;
                        Globals.loggedUserGpa = Double.parseDouble(obj.get("cgpa").toString());
                        getUser(Globals.logged_user_id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(login_form.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    name.setText("");
                    password.setText("");
                }
            });
            queue.add(req);

        }catch (Exception e){
            Toast.makeText(login_form.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void btn_signupform(View view) {
        startActivity(new Intent(getApplicationContext(),Signup.class));
    }


    private void getUser(int id){
         Globals.logged_user = new ArrayList<>();
        String url= Globals.BaseUrl+"survey/getUser?category="+Globals.logged_userCategory+"&id="+id;
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String str) {
                JSONObject response = null;
                User u = new User();
                if(Globals.logged_userCategory.equals("Student")){
                    try {

                        response = new JSONObject(str);
                        u.setId(response.getInt("id"));
                        u.setDiscipline(response.getString("discipline"));
                        u.setSection(response.getString("section"));
                        u.setDiscipline(response.getString("semester"));
                        Globals.logged_user.add(u);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(Globals.logged_userCategory.equals("Teacher")){

                    try {
                        response = new JSONObject(str);
                        u.setId(response.getInt("fclty_id"));
                        u.setName(response.getString("fclty_name"));
                        Globals.logged_user.add(u);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Log.d("RESPONSE:",str);
               Log.d("User",Globals.logged_user.get(0).getId()+"");
                Intent intent=new Intent(login_form.this,createsurvey.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("password",password.getText().toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login_form.this, "error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(req);
    }

}