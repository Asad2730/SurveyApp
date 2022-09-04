package com.example.firstapp.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.Models.previous_survey;
import com.example.firstapp.R;

import java.util.ArrayList;
import java.util.List;


public class previousSurveyAdopter extends RecyclerView.Adapter<previousSurveyAdopter.viewHolder> {
ArrayList<previous_survey> list;
Context context;

    public previousSurveyAdopter(Context context, ArrayList<previous_survey> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.sample_previous_survey,parent,false);
     return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
   // previous_survey model=list.get(position);
    holder.text.setText(list.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView text;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
              text= itemView.findViewById(R.id.surveyname);

        }
    }


}
