package com.example.firstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class createsurvey extends AppCompatActivity {
    private RequestQueue queue;
    EditText txt_titlesurvey;

    String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsurvey);

        //txt_titlesurvey=(TextView) findViewById(R.id.txt_surveytitle);
    }

    public void btn_create_survey(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(createsurvey.this);
        View mView = getLayoutInflater().inflate(R.layout.create_survey_curtom,null);

        Button btn_newsurvey = (Button) mView.findViewById(R.id.btn_newsurvey);
        Button btn_editsurvey = (Button) mView.findViewById(R.id.btn_editsurvey);
        Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
        Button pending = mView.findViewById(R.id.btn_pending);


        alert.setView(mView);


        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        //andr wala naya dailogbox jis ma survey ka title dyna ha

      btn_newsurvey.setOnClickListener(new View.OnClickListener() {
         @Override
       public void onClick(View view) {
             //alertDialog.dismiss();
           final AlertDialog.Builder alert = new AlertDialog.Builder(createsurvey.this);

             View mView = getLayoutInflater().inflate(R.layout.new_survey_dailog,null);

         txt_titlesurvey = (EditText) mView.findViewById(R.id.txt_surveytitle);
           Button btn_titlecancel = (Button) mView.findViewById(R.id.btn_titlecancel);
           Button btn_titlecreate = (Button) mView.findViewById(R.id.btn_titlecreate);
           alert.setView(mView);

             queue= Volley.newRequestQueue(createsurvey.this);
             queue.start();

           final AlertDialog alertDialog = alert.create();
           alertDialog.setCanceledOnTouchOutside(false);

           alertDialog.show();

         btn_titlecancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 alertDialog.dismiss();
             }
         });
             btn_titlecreate.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     try
                     {
                         if (txt_titlesurvey.getText().toString().isEmpty()) {
                             txt_titlesurvey.setError("enter survey Title");
                         }
                         else
                             {
                             final String surveyName = txt_titlesurvey.getText().toString();
                             final String uri = Globals.BaseUrl + "survey/addsurvey";
                             String Name=getIntent().getStringExtra("name");
                             JSONObject jsonData = new JSONObject();
                             jsonData.put("S_Title", surveyName);
                             jsonData.put("SC_ID",Globals.logged_user_id);

                             JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, uri, jsonData, new Response.Listener<JSONObject>() {
                                 @Override
                                 public void onResponse(JSONObject response) {

                                     try {
                                         Object jsn = response.get("S_ID");
                                         int id = Integer.parseInt(jsn.toString());
                                         Globals.survey_id = id;
                                         Log.d("RESPONSE_ID:",id+"");
                                          Intent intent=new Intent(createsurvey.this, questionare.class);
                                          intent.putExtra("surveyname",surveyName);
                                          intent.putExtra("S_ID",id);
                                          startActivity(intent);
                                          Toast.makeText(createsurvey.this, Name, Toast.LENGTH_LONG).show();
                                         txt_titlesurvey.setText("");
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }
                                     Log.d("RESPONSE:",response.toString());
                                     Toast.makeText(createsurvey.this, response.toString(), Toast.LENGTH_LONG).show();

                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                    // Log.d("RESPONSE:",error.getMessage());
                                     txt_titlesurvey.setText(error.getMessage());


                                 }
                             });
                             queue.add(req);

                         }
                     }
                     catch(Exception ex)
                     {
                         Toast.makeText(createsurvey.this, ex.toString(), Toast.LENGTH_LONG).show();
                     }

                    // txt_titlesurvey.setText(surveyname.getText().toString());


                 }
             });



        }
     });

     btn_editsurvey.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
//           startActivity(new Intent(getApplicationContext(), previous_surveyList.class));
            Intent intent=new Intent(createsurvey.this,previous_surveyList.class);
         //   String Name= intent.getStringExtra("name");
             String Name=getIntent().getStringExtra("name");
            String Pass = getIntent().getStringExtra("password");
             intent.putExtra("uname",Name);
             intent.putExtra("passwordch",Pass);
            // Toast.makeText(createsurvey.this, Name+""+Pass, Toast.LENGTH_SHORT).show();
             startActivity(intent);

         }
     });
    btn_cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            alertDialog.dismiss();
        }
    });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 pendingList();
            }
        });

    alertDialog.show();
    }
    void checkDataEntered() {
        if (isEmpty(txt_titlesurvey)) {
            Toast t = Toast.makeText(this, "You must enter survey Name!", Toast.LENGTH_LONG);
            t.show();
        }
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    private void pendingList(){
       try{
           Intent intent = new Intent(getApplicationContext(),PendingActivity.class);
           startActivity(intent);
       }catch (Exception ex){
           Log.d("EXCEPTION_PENDING",ex.getMessage());
       }

    }
}