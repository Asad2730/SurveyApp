package com.example.firstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class EditSurvey extends AppCompatActivity {
    TextView titlesurvey;
   Button edit,conduct,result;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey);
        getSupportActionBar().setTitle("Summary");

        titlesurvey=findViewById(R.id.displaysurveytitle);
        edit=findViewById(R.id.btn_edit);
        conduct=findViewById(R.id.btn_conduct);
        result=findViewById(R.id.btn_result);

        queue= Volley.newRequestQueue(this);
        queue.start();
        String title = getIntent().getStringExtra("surveyname1");
        Globals.surveyTitle = title;

        titlesurvey.setText(Globals.surveyTitle);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditSurvey.this,Edit_quersstionare.class);
                intent.putExtra("surveyname2", Globals.surveyTitle);
                startActivity(intent);
            }
        });
        
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(EditSurvey.this, "survey_id:"+Globals.survey_id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),AnalyizeActivity.class);
                startActivity(i);
            }
        });

        conduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(EditSurvey.this);
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
                        Intent intent=new Intent(EditSurvey.this,faculty_population.class);
                        startActivity(intent);
                    }
                });

                //student button
                btn_Student.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(EditSurvey.this, student_population.class);
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
}