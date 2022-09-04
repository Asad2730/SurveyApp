package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.QuestionsAdopter;
import com.example.firstapp.Models.QuestionsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class questionare extends AppCompatActivity {

    FloatingActionButton btnMul ;
    TextView txt_titlesurvey;
    RecyclerView recyclerView;
   // List<QuestionsModel> qmList=new ArrayList<>();

   AlertDialog dailog;
    ArrayList<QuestionsModel> list;
    QuestionsAdopter adopter;

    private RequestQueue queue;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(list!=null && radiobutton.data!=null){
            list.add(radiobutton.data);
            radiobutton.data=null;
            adopter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radiobutton.data=null;
        setContentView(R.layout.activity_questionare);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queue= Volley.newRequestQueue(this);
        queue.start();


        //adopter ko use kr rhy hn
        recyclerView = findViewById(R.id.Q_RecyclerView);


        list = new ArrayList<QuestionsModel>();

       // list.add((new QuestionsModel("first Question is yujejk","excelent","good","average","bad")));
       // list.add((new QuestionsModel("2nd Question is yutgniwue","excelent","good","average","bad")));
       // list.add((new QuestionsModel("3rd Question is yutgniwue","excelent","good","average","bad")));
       // list.add((new QuestionsModel("4th Question is yutgniwue","excelent","good","average","bad")));
       // list.add((new QuestionsModel("5th Question is yutgniwue","excelent","good","average","bad")));
      adopter=new QuestionsAdopter(list,this);
      recyclerView.setAdapter(adopter);

      LinearLayoutManager layoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(layoutManager);


       txt_titlesurvey = (TextView)findViewById(R.id.txt_title);
       String title_survey=getIntent().getStringExtra("surveyname");
       int sid = getIntent().getIntExtra("S_ID",0);

       txt_titlesurvey.setText(title_survey);

        btnMul =findViewById(R.id.btn_multichoice);



        //dailogbox sa survey ka title get kr rha ha
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("Enter survey title");
//
//        //Inflate the custom dailog view
//        View view=getLayoutInflater().inflate(R.layout.new_survey_dailog,null);
//       EditText surveyname=findViewById(R.id.txt_surveytitle);
//       Button btnTitleCreate=findViewById(R.id.btn_titlecreate);
//        surveyname.setText(txt_titlesurvey.getText().toString());
//
//
//
//
//        //set this view to dailog
//        builder.setView(view);
//        dailog=builder.create();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(questionare.this,radiobutton.class);
                intent.putExtra("S_ID",sid );
                startActivity(intent);
                Toast.makeText(questionare.this, "Add Question", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Toast.makeText(questionare.this, "send", Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id== R.id.send) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(questionare.this);
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
                    final AlertDialog.Builder alert = new AlertDialog.Builder(questionare.this);
                   // View mView = getLayoutInflater().inflate(R.layout.savesurvey,null);
                    Button Done;
                    Done=findViewById(R.id.done);
                    Done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
             btn_conduct.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                    // alertDialog.dismiss();
                     final AlertDialog.Builder alert = new AlertDialog.Builder(questionare.this);
                     View mView = getLayoutInflater().inflate(R.layout.population,null);

                     Button btn_Faculty =  mView.findViewById(R.id.btn_faculty);
                     Button btn_Student =  mView.findViewById(R.id.btn_student);
                     Button both =  mView.findViewById(R.id.both);
                     Button btn_Cancel =  mView.findViewById(R.id.btn_cancel);

                     alert.setView(mView);

                     final AlertDialog alertDialog = alert.create();
                     alertDialog.setCanceledOnTouchOutside(false);
                     alertDialog.show();

                     //Faculty button
                     btn_Faculty.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             Intent intent=new Intent(questionare.this,faculty_population.class);
                             startActivity(intent);
                         }
                     });

                     //student button
                     btn_Student.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             Intent intent=new Intent(questionare.this, student_population.class);
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


                             queue.add(arr);
                         }
                     });

                 }
             });
        }
        return true;
    }
}



//    public void btn_add(View view) ,{
//        bt_.setVisibility(View.VISIBLE);
//        ConstraintLayout pen = (ConstraintLayout) findViewById(R.id.btn_pen);
//        pen.setVisibility(View.VISIBLE);
//    }
