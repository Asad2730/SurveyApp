package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Models.Conduct;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class student_population extends AppCompatActivity {
    TextView decipline,semester,section;
    Button add;
    boolean[]selectedDecipline;
    ArrayList<Integer> classes = new ArrayList<>();
    String[] Deciline = {"BSCS(AI)","BSCS(GC)"};
    List<String> list_ai = new ArrayList<>(),list_gc;
    CheckBox[] ai = new CheckBox[8],gc = new CheckBox[8];
    CheckBox [] s_ai = new CheckBox[16],s_gc = new CheckBox[16];
    LinearLayout layout;
    Conduct conduct;
    List<Conduct> data,data_all;
    RadioButton all,specific;
    private RequestQueue queue;
    private double cgpa = 0;
    private EditText cgpaTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_population);
        getSupportActionBar().setTitle("Survey For Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);
        queue.start();
        layout = findViewById(R.id.layout);
        cgpaTxt = findViewById(R.id.cgpa);
        decipline = findViewById(R.id.select_decipline);
        semester = findViewById(R.id.select_Semester);
        section = findViewById(R.id.select_section);
        all = findViewById(R.id.all);
        specific = findViewById(R.id.specific);

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                all.setSelected(b);
                if(b){
                    layout.setVisibility(View.GONE);
                }
            }
        });

        specific.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                specific.setSelected(b);
                if(b){
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });


        add = findViewById(R.id.add);

        list_ai = new ArrayList<>();
        list_gc = new ArrayList<>();

        data = new ArrayList<>();

        selectedDecipline= new boolean[Deciline.length];

        decipline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disciplineBuilder();
            }
        });

        semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semesterBuilder();
            }
        });

        section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sectionBuilder();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(specific.isSelected()){
                    try {
                        insertData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(all.isSelected()){

                  initializeForAll();
                    try {
                        Toast.makeText(student_population.this, "yes", Toast.LENGTH_SHORT).show();
                        insertData(data_all);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    private  void initializeForAll(){
        data_all = new ArrayList<>();
        for(int i=0;i<8;i++){
            Conduct c = new Conduct();
            c.setuId(Globals.logged_user_id);
            c.setsId(Globals.survey_id);
            c.setSemester(i+1+"");
            c.setSection("a");
            c.setDiscipline("ai");
            Conduct aib = c;
            aib.setSection("b");
            Conduct gca = c;
            gca.setDiscipline("gc");
            Conduct gcb = gca;
            gcb.setSection("b");

            data_all.add(c);
            data_all.add(aib);
            data_all.add(gca);
            data_all.add(gcb);
        }
    }

    private void disciplineBuilder(){
        AlertDialog.Builder builder=new AlertDialog.Builder(student_population.this);
        builder.setTitle("Select Discipline");

        builder.setCancelable(false);
        builder.setMultiChoiceItems(Deciline, selectedDecipline, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b)
                {//when checkbox selectedd
                    //add position in decipline list
                    classes.add(i);
                    //sort day list
                    Collections.sort(classes);

                }
                else
                {
                    classes.remove(i);

                }
            }
        });

        //for ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder=new StringBuilder();
                //use for loop
                for (int j=0; j<classes.size(); j++){
                    //concat array values
                    stringBuilder.append(Deciline[classes.get(j)]);
                    //checck condition
                    if (j != classes.size()-1)
                    {
                        //when j value not equal to day list size -1
                        //add comma
                        stringBuilder.append(", ");

                    }
                }
                //set text on text view
                decipline.setText(stringBuilder.toString());
            }
        });

        //for cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //for clear all button

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for(int j=0; j<selectedDecipline.length;j++)
                {
                    selectedDecipline[j]= false;
                    classes.clear();
                    decipline.setText("");
                }
            }
        });
        builder.show();
    }


    private  void semesterBuilder(){

        AlertDialog.Builder builder = new AlertDialog.Builder(student_population.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.student_semester,null);
        builder.setView(v);
        TextView clear,ok,cancel;

        builder.setCancelable(false);

        ok = v.findViewById(R.id.ok);
        clear = v.findViewById(R.id.clear);
        cancel = v.findViewById(R.id.cancel);

        //set visibility on of
        ai[0] = v.findViewById(R.id.ai1);
        ai[1] = v.findViewById(R.id.ai2);
        ai[2] = v.findViewById(R.id.ai3);
        ai[3] = v.findViewById(R.id.ai4);
        ai[4] = v.findViewById(R.id.ai5);
        ai[5] = v.findViewById(R.id.ai6);
        ai[6] = v.findViewById(R.id.ai7);
        ai[7] = v.findViewById(R.id.ai8);

        gc[0] = v.findViewById(R.id.gc1);
        gc[1] = v.findViewById(R.id.gc2);
        gc[2] = v.findViewById(R.id.gc3);
        gc[3] = v.findViewById(R.id.gc4);
        gc[4] = v.findViewById(R.id.gc5);
        gc[5] = v.findViewById(R.id.gc6);
        gc[6] = v.findViewById(R.id.gc7);
        gc[7] = v.findViewById(R.id.gc8);

        setVisibilityCheckBoxes();


        AlertDialog dialog = builder.create();

        dialog.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder=new StringBuilder();
                for(int y=0;y<list_ai.size();y++){
                    stringBuilder.append(list_ai.get(y)+",");
                    Log.d("LISTAI:",list_ai.get(y));

                }

                for(int y=0;y<list_gc.size();y++){
                    Log.d("LISTGC:",list_gc.get(y));
                    stringBuilder.append(list_gc.get(y)+",");
                }

                semester.setText(stringBuilder.toString());
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


       clear.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            list_ai.clear();
            list_gc.clear();
            semester.setText("");
            dialog.dismiss();
        }
       });


        checkBoxesListen();


    }


    private  void sectionBuilder(){

        AlertDialog.Builder builder = new AlertDialog.Builder(student_population.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.student_section,null);
        builder.setView(v);
        TextView clear,ok,cancel;
        builder.setCancelable(false);

        ok = v.findViewById(R.id.ok);
        clear = v.findViewById(R.id.clear);
        cancel = v.findViewById(R.id.cancel);

        //set visibility on of
        s_ai[0] = v.findViewById(R.id.ai1a);
        s_ai[1] = v.findViewById(R.id.ai1b);
        s_ai[2] = v.findViewById(R.id.ai2a);
        s_ai[3] = v.findViewById(R.id.ai2b);
        s_ai[4] = v.findViewById(R.id.ai3a);
        s_ai[5] = v.findViewById(R.id.ai3b);
        s_ai[6] = v.findViewById(R.id.ai4a);
        s_ai[7] = v.findViewById(R.id.ai4b);

        s_ai[8] = v.findViewById(R.id.ai5a);
        s_ai[9] = v.findViewById(R.id.ai5b);
        s_ai[10] = v.findViewById(R.id.ai6a);
        s_ai[11] = v.findViewById(R.id.ai6b);
        s_ai[12] = v.findViewById(R.id.ai7a);
        s_ai[13] = v.findViewById(R.id.ai7b);
        s_ai[14] = v.findViewById(R.id.ai8a);
        s_ai[15] = v.findViewById(R.id.ai8b);

        s_gc[0] = v.findViewById(R.id.gc1a);
        s_gc[1] = v.findViewById(R.id.gc1b);
        s_gc[2] = v.findViewById(R.id.gc2a);
        s_gc[3] = v.findViewById(R.id.gc2b);
        s_gc[4] = v.findViewById(R.id.gc3a);
        s_gc[5] = v.findViewById(R.id.gc3b);
        s_gc[6] = v.findViewById(R.id.gc4a);
        s_gc[7] = v.findViewById(R.id.gc4b);

        s_gc[8] = v.findViewById(R.id.gc5a);
        s_gc[9] = v.findViewById(R.id.gc5b);
        s_gc[10] = v.findViewById(R.id.gc6a);
        s_gc[11] = v.findViewById(R.id.gc6b);
        s_gc[12] = v.findViewById(R.id.gc7a);
        s_gc[13] = v.findViewById(R.id.gc7b);
        s_gc[14] = v.findViewById(R.id.gc8a);
        s_gc[15] = v.findViewById(R.id.gc8b);

        int s = 1;
        for(int i=0;i<16;i=i+2){
            s_ai[i].setText(s+"-A");
            s_ai[i+1].setText(s+"-B");
            s_gc[i].setText(s+"-A");
            s_gc[i+1].setText(s+"-B");
            s++;

        }
        sectionChkListeners();
        setVisibilityCheckBoxesSection();


        AlertDialog dialog = builder.create();

        dialog.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder=new StringBuilder();
                for(int y=0;y<data.size();y++){
                    stringBuilder.append(data.get(y).getSemester()+"-"+data.get(y).getSection()+",");
                }


                section.setText(stringBuilder.toString());
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_ai.clear();
                list_gc.clear();
                semester.setText("");
                dialog.dismiss();
            }
        });


    }




    private  void checkBoxesListen(){

       for(int i=0;i<8;i++){
           int val =i+1;
           ai[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if(b){
                       if(!list_ai.contains(val+"")){
                           list_ai.add(val+"");
                       }
                   }else{
                       list_ai.remove(val+"");
                   }
               }
           });
       }

        for(int i=0;i<8;i++){
            int val = i+1;
            gc[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        if(!list_gc.contains(val+"")) {
                            list_gc.add(val + "");
                        }
                    }else{
                        list_gc.remove(val+"");
                    }
                }
            });
        }

    }


    private  void setVisibilityCheckBoxes(){

        for(int i=0;i<8;i++){
            int c = classes.size();
            
          if(c == 1 || c == 2){
              ai[i].setVisibility(View.VISIBLE);
          }else{
              ai[i].setVisibility(View.GONE);
          }

            if(c == 2){
                gc[i].setVisibility(View.VISIBLE);
            }
            else{
                gc[i].setVisibility(View.GONE);
            }
        }

    }

    private void setVisibilityCheckBoxesSection() {

        int sec = 0;
        for (int i=0;i<16;i=i+2){
            sec++;
            if(list_ai.contains(sec+"")){
                s_ai[i].setVisibility(View.VISIBLE);
                s_ai[i+1].setVisibility(View.VISIBLE);
            }else{
                s_ai[i].setVisibility(View.GONE);
                s_ai[i+1].setVisibility(View.GONE);
            }

            if(list_gc.contains(sec+"")){
                s_gc[i].setVisibility(View.VISIBLE);
                s_gc[i+1].setVisibility(View.VISIBLE);
            }else{
                s_gc[i].setVisibility(View.GONE);
                s_gc[i+1].setVisibility(View.GONE);
            }

        }
    }

   private  void sectionChkListeners(){

        for(int i=0;i<16;i=i+2){

            s_ai[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CheckBox bx = (CheckBox) compoundButton;
                    String[] sec = bx.getText().toString().split("-");
                    //Toast.makeText(student_population.this, "bx:"+sec[0], Toast.LENGTH_SHORT).show();
                    conduct = new Conduct();
                    conduct.setDiscipline("ai");
                    conduct.setSemester(sec[0]);
                    conduct.setSection("a");
                    conduct.setsId(Globals.survey_id);
                    conduct.setuId(Globals.logged_user_id);
                    if(b){
                       if(!data.contains(conduct)){
                           data.add(conduct);
                       }
                    }else{
                     data.remove(conduct);
                    }
                }
            });

            s_ai[i+1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CheckBox bx = (CheckBox) compoundButton;
                    String[] sec = bx.getText().toString().split("-");
                    conduct = new Conduct();
                    conduct.setDiscipline("ai");
                    conduct.setSemester(sec[0]);
                    conduct.setSection("b");
                    conduct.setsId(Globals.survey_id);
                    conduct.setuId(Globals.logged_user_id);
                    if(b){
                        if(!data.contains(conduct)){
                            data.add(conduct);
                        }
                    }else{
                        data.remove(conduct);
                    }
                }
            });


        }

       for(int i=0;i<16;i=i+2){

           s_gc[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   CheckBox bx = (CheckBox) compoundButton;
                   String[] sec = bx.getText().toString().split("-");
                   conduct = new Conduct();
                   conduct.setDiscipline("gc");
                   conduct.setSemester(sec[0]);
                   conduct.setSection("a");
                   conduct.setsId(Globals.survey_id);
                   conduct.setuId(Globals.logged_user_id);
                   if(b){
                       if(!data.contains(conduct)){
                           data.add(conduct);
                       }
                   }else{
                       data.remove(conduct);
                   }
               }
           });

           s_gc[i+1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   CheckBox bx = (CheckBox) compoundButton;
                   String[] sec = bx.getText().toString().split("-");
                   conduct = new Conduct();
                   conduct.setDiscipline("gc");
                   conduct.setSemester(sec[0]);
                   conduct.setSection("b");
                   conduct.setsId(Globals.survey_id);
                   conduct.setuId(Globals.logged_user_id);
                   if(b){
                       if(!data.contains(conduct)){
                           data.add(conduct);
                       }
                   }else{
                       data.remove(conduct);
                   }
               }
           });


       }

   }


   private void insertData(List<Conduct> list) throws JSONException {

      // Intent intent = new Intent(student_population.this,createsurvey.class);
       if(!cgpaTxt.getText().toString().isEmpty()){
           cgpa = Double.parseDouble(cgpaTxt.getText().toString());
       }
        String url = Globals.BaseUrl +"survey/postSurveyStudent";
        for(int i=0;i<list.size();i++){
            int size = i;
            JSONObject json = new JSONObject();
            json.put("sid",list.get(i).getsId());
            json.put("uid",list.get(i).getuId());
            json.put("discipline",list.get(i).getDiscipline());
            json.put("section",list.get(i).getSection());
            json.put("semester",list.get(i).getSemester());
            json.put("cgpa",cgpa);

            String d = list.get(i).getDiscipline()+"-"+list.get(i).getSemester()+list.get(i).getSection();
            Log.d("DATA:",d);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(size == list.size() - 1){
                        Toast.makeText(student_population.this, "DataSaved..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),DateActivity.class));
                    }
                }
            },null );
            queue.add(req);
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