package com.example.firstapp.Adopters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Globals;
import com.example.firstapp.Models.Survey;
import com.example.firstapp.R;
import com.example.firstapp.createsurvey;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PendingSurveyAdapter extends BaseAdapter {
    private Context context;
    private List<Survey> list;
    private TextView question;
    private RadioButton op1,op2,op3,op4;
    private Button done,back;
    private LayoutInflater inflater;
    private HashMap<String,Values> map = new HashMap<>();
    private RequestQueue queue;

    public PendingSurveyAdapter(Context context,List<Survey> list){

        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        queue = Volley.newRequestQueue(context);
        queue.start();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Survey getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View v, ViewGroup viewGroup) {
        v = inflater.inflate(R.layout.pending_questions_row, null);

        done = v.findViewById(R.id.ok); //remove current question
        back = v.findViewById(R.id.back);

        question = v.findViewById(R.id.question);
        op1 = v.findViewById(R.id.op1);
        op2 = v.findViewById(R.id.op2);
        op3 = v.findViewById(R.id.op3);
        op4 = v.findViewById(R.id.op4);

        question.setText(getItem(position).getQuestion());
        op1.setText(getItem(position).getOp1());
        op2.setText(getItem(position).getOp2());
        op3.setText(getItem(position).getOp3());
        op4.setText(getItem(position).getOp4());

        op1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String btn = getItem(position).getQuestion();
                if(b){

                    int id = getItem(position).getQ_id();
                    Values v = new Values();
                    v.qid = id;
                    v.answer = getItem(position).getOp1();
                    if(map.containsKey(btn)){
                     map.replace(btn,v);
                    }else {
                        map.put(btn,v);
                    }

                }

            }
        });

        op2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String btn = getItem(position).getQuestion();
                if(b){
                    int id = getItem(position).getQ_id();
                    Values v = new Values();
                    v.qid = id;
                    v.answer = getItem(position).getOp2();
                    if(map.containsKey(btn)){
                        map.replace(btn,v);
                    }else {
                        map.put(btn,v);
                    }
                }
            }
        });
        op3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String btn = getItem(position).getQuestion();
                if(b){
                    int id = getItem(position).getQ_id();
                    Values v = new Values();
                    v.qid = id;
                    v.answer = getItem(position).getOp3();
                    if(map.containsKey(btn)){
                        map.replace(btn,v);
                    }else {
                        map.put(btn,v);
                    }

                }

            }
        });
        op4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    String btn = getItem(position).getQuestion();
                int id = getItem(position).getQ_id();
                Values v = new Values();
                v.qid = id;
                v.answer = getItem(position).getOp4();
                if(map.containsKey(btn)){
                    map.replace(btn,v);
                }else {
                    map.put(btn,v);
                }


            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  boolean chk = map.containsKey(getItem(position).getQuestion());
                  if(chk){
                       int qid = map.get(getItem(position).getQuestion()).qid;
                       int sid = getItem(position).getSurvey_id();
                       int uid = Globals.logged_user_id;
                       String answer =  map.get(getItem(position).getQuestion()).answer;
                       Log.d("QID",qid+"");
                       Log.d("ANSWER",answer);
                    String url = Globals.BaseUrl+"survey/postAnswer?qid="+qid+"&sid="+sid+"&uid="+uid+"&answer="+answer;
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                              new Response.Listener<JSONObject>() {
                          @Override
                          public void onResponse(JSONObject response) {
                              Log.d("SIZE",list.size()+"");
                              if(list.size() == 1){
                                  Intent intent = new Intent(context.getApplicationContext(),createsurvey.class);
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                  context.startActivity(intent);
                              }else {
                                  list.remove(position);
                                  PendingSurveyAdapter.this.notifyDataSetChanged();
                              }
                          }
                      },null);
                      queue.add(request);

                }
            }
        });

        back.setVisibility(View.GONE);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((Activity)context).finish();
//            }
//        });

        return v;
    }

}

 class Values{
   public int qid;
   public String answer;
}
