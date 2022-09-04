package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.PendingAdapter;
import com.example.firstapp.Models.Survey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PendingActivity extends AppCompatActivity {

    private ListView list;
    private RequestQueue queue;
    private List<Survey> surveyList;
    private  Survey s;
    private PendingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        queue= Volley.newRequestQueue(this);
        queue.start();
        list = findViewById(R.id.list);
        loadList();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),PendingQuestionsActivity.class);
                Globals.surveyTitle = surveyList.get(i).getTitle();
                startActivity(intent);

            }
        });
    }

    private void loadList(){
        surveyList = new ArrayList<>();
        String url = Globals.BaseUrl+"survey/getSurveys";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        Log.d("Response",response.toString());
                        int sid = response.getJSONObject(i).getInt("S_ID");
                        String title = response.getJSONObject(i).getString("S_Title");
                        s = new Survey();
                        s.setSurvey_id(sid);
                        s.setTitle(title);
                        surveyList.add(s);
                        adapter = new PendingAdapter(getApplicationContext(),surveyList);
                        list.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },null);
        queue.add(request);
    }
}