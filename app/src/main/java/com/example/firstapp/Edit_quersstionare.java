package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.QuestionsAdopter;
import com.example.firstapp.Adopters.previousSurveyAdopter;
import com.example.firstapp.Models.QuestionsModel;
import com.example.firstapp.Models.previous_survey;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Edit_quersstionare extends AppCompatActivity {

    FloatingActionButton btnMul ;

    TextView txt_titlesurvey;
    RecyclerView recyclerView;
    // List<QuestionsModel> qmList=new ArrayList<>();
    RequestQueue req;
    AlertDialog dailog;
    ArrayList<QuestionsModel> list;
    QuestionsAdopter adopter;
    String title_survey;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(list!=null && radiobutton.data!=null){
            list.add(radiobutton.data);
            radiobutton.data=null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quersstionare);
        getSupportActionBar().setTitle("Edit");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.edit_Q_RecyclerView);

        req = Volley.newRequestQueue(this);
        req.start();

        list = new ArrayList<>();


//        adopter=new QuestionsAdopter(list,this);
//        recyclerView.setAdapter(adopter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);


        txt_titlesurvey = findViewById(R.id.edit_txt_title);

        title_survey = getIntent().getStringExtra("surveyname2");

        txt_titlesurvey.setText(title_survey);

        btnMul = findViewById(R.id.edit_btn_multichoice);

        loadList();



        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Edit_quersstionare.this,radiobutton.class);
                startActivity(intent);
                Toast.makeText(Edit_quersstionare.this, "Add Question", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void loadList(){
        try{

            String uri = Globals.BaseUrl + "survey/getQuestionSurvey?sid="+Globals.survey_id;
            JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, uri, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                QuestionsModel s = new QuestionsModel();
                                try {
                                    Log.d("tag", ""+response.toString());
                                    // JSONObject jsonObject=response.getJSONObject(i);
                                    s.setText(response.getJSONObject(i).getString("Q_Description"));
                                    s.setOption1(response.getJSONObject(i).getString("Q_Option1"));
                                    s.setOption2(response.getJSONObject(i).getString("Q_Option2"));
                                    s.setOption3(response.getJSONObject(i).getString("Q_Option3"));
                                    s.setOption4(response.getJSONObject(i).getString("Q_Option4"));

                                    list.add(s);
                                     adopter = new QuestionsAdopter(list, getApplicationContext());
                                    recyclerView.setAdapter(adopter);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Toast.makeText(Edit_quersstionare.this, "send", Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id== R.id.send) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(Edit_quersstionare.this);
            View mView = getLayoutInflater().inflate(R.layout.conduct_save,null);

            Button btn_save = (Button) mView.findViewById(R.id.btn_saveSurvey);
            Button btn_conduct = (Button) mView.findViewById(R.id.btn_ConductSurvey);
            // Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);

            alert.setView(mView);

            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();



            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();

                }
            });
            btn_conduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // alertDialog.dismiss();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Edit_quersstionare.this);
                    View mView = getLayoutInflater().inflate(R.layout.population,null);

                    Button btn_Faculty = (Button) mView.findViewById(R.id.btn_faculty);
                    Button btn_Student = (Button) mView.findViewById(R.id.btn_student);
                    Button both = (Button) mView.findViewById(R.id.both);
                    Button btn_Cancel = (Button) mView.findViewById(R.id.btn_cancel);

                    alert.setView(mView);

                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();


                    both.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String url =Globals.BaseUrl+"survey/sendSurveyToAll?sid="+Globals.survey_id+
                                    "&uid="+Globals.logged_user_id;
                            Log.d("URL",url);
                            JsonArrayRequest arr = new JsonArrayRequest(Request.Method.POST, url, null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            Toast.makeText(getApplicationContext(), "Send Successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),DateActivity.class));
                                        }
                                    },null);

                            req.add(arr);
                        }
                    });

                    //Faculty button
                    btn_Faculty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(Edit_quersstionare.this,faculty_population.class);
                            startActivity(intent);
                        }
                    });

                    //student button
                    btn_Student.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(Edit_quersstionare.this, student_population.class);
                            startActivity(intent);

                        }
                    });

                    //cancel buttom
                    btn_Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });




        }
        return true;
    }
}