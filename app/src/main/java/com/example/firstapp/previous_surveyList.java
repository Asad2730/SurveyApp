package com.example.firstapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.previousSurveyAdopter;
import com.example.firstapp.Classes.RecyclerItemClickListener;
import com.example.firstapp.Models.previous_survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class previous_surveyList extends AppCompatActivity {
     private RecyclerView recyclerView;

    private previousSurveyAdopter adapter;
    private ArrayList<previous_survey> std = new ArrayList<>();
    private RequestQueue req;
    EditText name1,pass1;
    String Name,Pass,titlename;
   // TextView abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_survey_list);

        req = Volley.newRequestQueue(this);
        req.start();
     //   abc=findViewById(R.id.sabc);
 //View mView = getLayoutInflater().inflate(R.layout.activity_login_form,null);
        name1=findViewById(R.id.name);
        pass1=findViewById(R.id.pass);

       // Intent intent=getIntent();
        Name= getIntent().getStringExtra("uname");
        Pass = getIntent().getStringExtra("passwordch");
//        Toast.makeText(this, Name+" && "+Pass, Toast.LENGTH_SHORT).show();

//         Name=name1.getText().toString();
//         Pass=pass1.getText().toString();
       // Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
        recyclerView=findViewById(R.id.previous_recycler);
       //ArrayList<previous_survey> list = new ArrayList<>();
//        list.add(new previous_survey("corona"));
//        list.add(new previous_survey("Gala Function"));
//        list.add(new previous_survey("Hijab"));
//        list.add(new previous_survey("Student week"));
//    list.add(new previous_survey("Saturday off"));
        loadList();
        Toast.makeText(this, Name+" && "+Pass, Toast.LENGTH_SHORT).show();
      recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
              (this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                  @Override
                  public void onItemClick(View view, int position) {
                      //previous_survey stitle=new previous_survey();
                     // String title=stitle.getText().toString();
                      Toast.makeText(getApplicationContext(), "sid"+std.get(position).getId(), Toast.LENGTH_SHORT).show();
                      Globals.survey_id = std.get(position).getId();
                     Intent intent = new Intent(previous_surveyList.this, EditSurvey.class);
                      //Toast.makeText(previous_surveyList.this, Name, Toast.LENGTH_SHORT).show();
                      intent.putExtra("surveyname1",std.get(position).getText());
                      startActivity(intent);


                    // Log.d(TAG, "onItemClick: itemclick"+position);
                  }

                  @Override
                  public void onLongItemClick(View view, int position) {

                  }
              }

              ));

    }
    private void loadList(){
        try{
            std.clear();
            String uri = Globals.BaseUrl + "survey/GetUserSurvey?id="+Globals.logged_user_id;
            JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, uri, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {


                            for (int i = 0; i < response.length(); i++) {
                                previous_survey s = new previous_survey();
                                try {
                                    Log.d("tag", ""+response.getJSONObject(i).getString("S_Title"));
                                    // JSONObject jsonObject=response.getJSONObject(i);
                                    s.setText(response.getJSONObject(i).getString("S_Title").toString());
                                    s.setId(response.getJSONObject(i).getInt("S_ID"));
//                                    s.setCgpa(response.getJSONObject(i).getString("cgpa"));
//                                    s.setSection(response.getJSONObject(i).getString("section"));

                                    std.add(s);
                                    previousSurveyAdopter adopter=new previousSurveyAdopter(getApplicationContext(), std);
                                    recyclerView.setAdapter(adopter);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);

//                                    adapter = new previousSurveyAdopter(getApplicationContext(),std);
//                                    list.setAdapter(adapter);
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



        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Exception-Load-List:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}