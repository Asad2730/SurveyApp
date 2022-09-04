package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.FacultyAdapter;
import com.example.firstapp.Models.FacultyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class faculty_population extends AppCompatActivity {

RadioButton rbAll,rbSpecific;
    ListView listViewData;
    List<FacultyModel> list;
    FacultyModel model;
    FacultyAdapter adapter;
    private Button save;
    private RequestQueue queue;
   private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_population);

        rbAll = findViewById(R.id.rbAll);
        rbSpecific = findViewById(R.id.rbSpecific);
        save = findViewById(R.id.save);
        layout = findViewById(R.id.view);
        queue= Volley.newRequestQueue(this);
        queue.start();

        rbSpecific.setChecked(true);
        rbAll.setSelected(false);
        rbSpecific.setSelected(true);
        rbSpecific.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                layout.setVisibility(View.VISIBLE);
                rbSpecific.setSelected(true);
                rbAll.setSelected(false);

            }
            else{
                rbSpecific.setChecked(false);
                rbAll.setChecked(true);
                layout.setVisibility(View.GONE);
            }
        });

        getSupportActionBar().setTitle("Survey For Faculty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewData=findViewById(R.id.listview_data);
        list = new ArrayList<>();

        Globals.facultyIds = new ArrayList<>();
        loadFaculty();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(rbAll.isSelected())  {
                  List<Integer> fids = new ArrayList<>();
                  for(int i=0;i<50;i++){
                      fids.add(i+1);
                  }
                  add(fids);
              }
              if(rbSpecific.isSelected()){
                  add(Globals.facultyIds);
              }

            }
        });
    }



   private void loadFaculty(){

        String url = Globals.BaseUrl+"survey/getFacalty";
       JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               for(int i=0;i<response.length();i++){
                   try {
                       int id = response.getJSONObject(i).getInt("fclty_id");
                       String name = response.getJSONObject(i).getString("fclty_name");
                       String gender = response.getJSONObject(i).getString("fclty_gender");
                       model = new FacultyModel();
                       model.setId(id);
                       model.setName(name);
                       model.setGender(gender);
                       list.add(model);
                       adapter = new FacultyAdapter(getApplicationContext(),list);
                       listViewData.setAdapter(adapter);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }


               }
           }
       },null);

       queue.add(arr);
    }


    private void add(List<Integer> fid){

        String url = Globals.BaseUrl+"survey/postFacultySurvey";
       // Intent intent = new Intent(faculty_population.this,createsurvey.class);

        for(int i = 0;i<Globals.facultyIds.size();i++){
            int size = i;
            Log.d("FID:",Globals.facultyIds.get(i)+"");
            JSONObject obj = new JSONObject();
            try {
                obj.put("uid",Globals.logged_user_id);
                obj.put("fid",fid.get(i));
                obj.put("sid",Globals.survey_id);

                JsonObjectRequest jsn = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       if(size == Globals.facultyIds.size()-1){
                           Toast.makeText(faculty_population.this, "DataSaved!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),DateActivity.class));
                       }
                    }
                }, null);
                queue.add(jsn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    
//SHARE ICON
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       // int id = item.getItemId();
        //if (id == R.id.share) {
          //  ApplicationInfo api = getApplicationContext().getApplicationInfo();
            //String apkpath = api.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "check out this cool application");
            intent.putExtra(Intent.EXTRA_TEXT, "your application link is here");
            startActivity(Intent.createChooser(intent, "shareVia"));
       /// }
        return true;
    }
}