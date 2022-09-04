package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.AdminNotificationAdapter;
import com.example.firstapp.Models.AdminNotificationModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AdminNotificationForm extends AppCompatActivity {

  private ListView lv;
  private List<AdminNotificationModel> list;
  private AdminNotificationAdapter adapter;
  private  RequestQueue req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification_form);
       req= Volley.newRequestQueue(this);
       req.start();
      lv=findViewById(R.id.list);
      list=new ArrayList();
      showNotification();

    }

    private void showNotification() {
        try {

            String uri = Globals.BaseUrl + "survey/GetNotificationSurvey";
            JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, uri, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Toast.makeText(AdminNotificationForm.this, "here", Toast.LENGTH_SHORT).show();
                            Log.d("RESPONSE::",response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                AdminNotificationModel s = new AdminNotificationModel();
                                try {

                                    s.setSurvey_id(response.getJSONObject(i).getInt("id"));
                                    s.setSurveyName(response.getJSONObject(i).getString("title"));
                                    s.setSc_name(response.getJSONObject(i).getString("name"));
                                    s.setUname(response.getJSONObject(i).getString("uName"));
                                    s.setEmail(response.getJSONObject(i).getString("email"));
                                    list.add(s);
                                    adapter = new AdminNotificationAdapter(getApplicationContext(),list);
                                    lv.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("tag", "onErrorResponse: "+error.getMessage());
                }
            });

            req.add(arr);




        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Exception-Load-List:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}