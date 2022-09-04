package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Models.QuestionsModel;

import org.json.JSONObject;


public class radiobutton extends AppCompatActivity {
 Button saveS ,cancelS;
 EditText Question;

 final String q1= "Extremely healthy",q2="Very healthy",q3="Not so healthy",q4="Not at all healthy";
 EditText Option1, Option2,Option3, Option4;
    private RequestQueue queue;

    //pendingSurvey ..whose answers have not been given by user
    //once submited cant be changed

public static QuestionsModel data;

    //@SuppressLint("WrongViewCast")
   // @SuppressLint("WrongViewCast")
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiobutton);
        getSupportActionBar().setTitle("RadioButton");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);
        queue.start();


        saveS=findViewById(R.id.save);
        cancelS=findViewById(R.id.cancel);
        Question=findViewById(R.id.questions);
        Option1=findViewById(R.id.option1);
        Option2=findViewById(R.id.option2);
        Option3=findViewById(R.id.option3);
        Option4=findViewById(R.id.option4);

        Option1.setText(q1);
        Option2.setText(q2);
        Option3.setText(q3);
        Option4.setText(q4);

        Option1.setFocusableInTouchMode(false);
        Option2.setFocusableInTouchMode(false);
        Option3.setFocusableInTouchMode(false);
        Option4.setFocusableInTouchMode(false);


      //  namesurvey=findViewById(R.id.txt_title);

        saveS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Question.getText().toString().isEmpty()) {
                    Question.setError("enter Question");
                }else{
                    if (Option1.getText().toString().isEmpty()) {
                        Option1.setError("enter option");
                    }else {
                            if (Option2.getText().toString().isEmpty()) {
                                Option2.setError("enter atleast 2 options");
                            } else {
                                save();
                            }
                        }
                }

            }
        });
        cancelS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void save()
    {
        try {

            final String Description=Question.getText().toString();
            final String choice1=Option1.getText().toString();

            final String choice2=Option2.getText().toString();
            final String choice3 =Option3.getText().toString();
            final String choice4 =Option4.getText().toString();
            final int sid =   Globals.survey_id;

            final String uri=Globals.BaseUrl+"survey/addquestion";


            radiobutton.data = new QuestionsModel(Description,choice1,choice2,choice3,choice4);

            JSONObject jsonData = new JSONObject();
            jsonData.put("Q_Description",Description);
            jsonData.put("Q_Option1",choice1);
            jsonData.put("Q_Option2",choice2);
            jsonData.put("Q_Option3",choice3);
            jsonData.put("Q_Option4",choice4);
            jsonData.put("sid",sid);
           // jsonData.put("S_name",NameSurvey);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, uri,jsonData, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Intent intent=new Intent(getApplicationContext(),Edit_quersstionare.class);
                    intent.putExtra("surveyname2", Globals.surveyTitle);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Intent intent=new Intent(getApplicationContext(),Edit_quersstionare.class);
                    intent.putExtra("surveyname2", Globals.surveyTitle);
                    startActivity(intent);
                }
            });
            queue.add(req);
        }
        catch (Exception e)
        {
            Toast.makeText(radiobutton.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    void checkDataEntered() {
        if (isEmpty(Question)) {
            Toast t = Toast.makeText(this, "You must enter question of your survey!", Toast.LENGTH_LONG);
            t.show();
        }
        if (isEmpty(Option1)) {
            Toast t = Toast.makeText(this, "You must enter atleast 2 option!", Toast.LENGTH_LONG);
            t.show();
        }
        if (isEmpty(Option2)) {
            Toast t = Toast.makeText(this, "You must enter atleast 2 option!", Toast.LENGTH_LONG);
            t.show();
        }

    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}