package com.example.firstapp.Adopters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.AdminDetailActivity;
import com.example.firstapp.Globals;
import com.example.firstapp.Models.AdminNotificationModel;
import com.example.firstapp.R;

import org.json.JSONObject;

import java.util.List;

public class AdminNotificationAdapter extends BaseAdapter {

    private Context context;
    private  List<AdminNotificationModel> list;
    private LayoutInflater inflater;
    private TextView sName,cName;
    private Button detail,approve,deny;
    private RequestQueue que;

    public AdminNotificationAdapter(Context context, List<AdminNotificationModel> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        que = Volley.newRequestQueue(context);
        que.start();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AdminNotificationModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        v = inflater.inflate(R.layout.admin_notification, null);
        sName = v.findViewById(R.id.Sname);
        cName=v.findViewById(R.id.Cname);

        sName.setText(list.get(i).getSurveyName());
        cName.setText(list.get(i).getUname());

        detail=v.findViewById(R.id.detailsurvey);
        approve=v.findViewById(R.id.Approve);
        deny=v.findViewById(R.id.Deny);



        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Survey Approved", Toast.LENGTH_SHORT).show();
                String approve="approve";
                int id = list.get(i).getSurvey_id();
                noteShow(id,approve);
                list.remove(i);
                AdminNotificationAdapter.this.notifyDataSetChanged();

            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Survey Deny", Toast.LENGTH_SHORT).show();
                String approve="deny";
                int id = list.get(i).getSurvey_id();
                noteShow(id,approve);
               list.remove(i);
                AdminNotificationAdapter.this.notifyDataSetChanged();

            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HERE",list.get(i).getEmail());
                Intent intent = new Intent(context, AdminDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",list.get(i).getUname());
                intent.putExtra("email",list.get(i).getEmail());
                context.startActivity(intent);
            }
        });

        return v;
    }


    private void noteShow(int id,String status){

        String uri= Globals.BaseUrl + "survey/modifystatus?id="+id+"&status="+status;
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE:", response.toString());
            }
        },null);

        que.add(obj);

    }
}
