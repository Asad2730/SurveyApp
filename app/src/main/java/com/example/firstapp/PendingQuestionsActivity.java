package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.Adopters.PendingSurveyAdapter;
import com.example.firstapp.Models.Survey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PendingQuestionsActivity extends AppCompatActivity {

   private ListView listView;
   private Survey s;
    private RequestQueue queue;
    private List<Survey> list;
   private PendingSurveyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_questions);
        queue = Volley.newRequestQueue(this);
        queue.start();
        listView = findViewById(R.id.list_view);
        loadList();

        //make db of attempted qs from user send uid there to not show this question
        //on button click remove from list  to and reload list or notify adapter

    }

    private void loadList(){
        String url = "";
        list = new ArrayList<>();

        Log.d("ID:",Globals.logged_user_id+"");
        if(Globals.logged_userCategory.equals("Teacher")){
            url = Globals.BaseUrl+"survey/getTeacherSurvey?title="+Globals.surveyTitle+
                    "&fid="+Globals.logged_user_id;
        }else{
            url = Globals.BaseUrl+"survey/getStudentSurvey?title="+Globals.surveyTitle+
                    "&sem_id="+Globals.logged_user.get(0).getId()+"&uid="+Globals.logged_user_id+"&cgpa="+Globals.loggedUserGpa;
            Log.d("URL",url);
        }

        JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("RESPONSE:::",response.toString());
                for(int i=0;i<response.length();i++){

                    try {
                        s = new Survey();
                        s.setSurvey_id(response.getJSONObject(i).getInt("sid"));
                        s.setQ_id(response.getJSONObject(i).getInt("Q_ID"));
                        s.setQuestion(response.getJSONObject(i).getString("Q_Description"));
                        s.setOp1(response.getJSONObject(i).getString("Q_Option1"));
                        s.setOp2(response.getJSONObject(i).getString("Q_Option2"));
                        s.setOp3(response.getJSONObject(i).getString("Q_Option3"));
                        s.setOp4(response.getJSONObject(i).getString("Q_Option4"));

                        list.add(s);
                        adapter = new PendingSurveyAdapter(getApplicationContext(),list);
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },null);
        queue.add(arr);
    }
}