package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.ResultAdapter;
import com.example.firstapp.Models.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AnalyizeActivity extends AppCompatActivity {

     private ListView view;
     private List<Result> list;
     private ResultAdapter adapter;
    private RequestQueue queue;
    private Button re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyize);
        re = findViewById(R.id.btn);
        queue= Volley.newRequestQueue(this);
        queue.start();
        view = findViewById(R.id.list);
        list = new ArrayList<>();
        load();
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             reDo();
            }
        });
    }

    private  void load(){

        String url = Globals.BaseUrl+"survey/analayizeSurvey?sid="+Globals.survey_id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d("RESPONSE",response.toString());
                 for(int i=0;i<response.length();i++){
                     try {
                         Result r = new Result();
                         r.setQ(response.getJSONObject(i).getString("q"));
                         r.setO1(response.getJSONObject(i).getString("o1"));
                         r.setO2(response.getJSONObject(i).getString("o2"));
                         r.setO3(response.getJSONObject(i).getString("o3"));
                         r.setO4(response.getJSONObject(i).getString("o4"));
                         r.setOp1(response.getJSONObject(i).getDouble("op1"));
                         r.setOp2(response.getJSONObject(i).getDouble("op2"));
                         r.setOp3(response.getJSONObject(i).getDouble("op3"));
                         r.setOp4(response.getJSONObject(i).getDouble("op4"));
                         list.add(r);
                         adapter = new ResultAdapter(getApplicationContext(),list);
                         view.setAdapter(adapter);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
            }
        },null);

      queue.add(request);
    }


  private void reDo(){
      String url = Globals.BaseUrl+"survey/reScheduleSurvey?sid="+Globals.survey_id;
      JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {

              try {
                  int id = response.getInt("S_ID");
                  Log.d("ID",id+"");
                  Globals.survey_id = id;
                  startActivity(new Intent(getApplicationContext(),DateActivity.class));
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      },null);
      queue.add(obj);

  }

}