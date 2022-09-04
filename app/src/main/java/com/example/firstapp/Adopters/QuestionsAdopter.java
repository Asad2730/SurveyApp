package com.example.firstapp.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.Models.QuestionsModel;
import com.example.firstapp.R;

import java.util.ArrayList;

public class QuestionsAdopter extends RecyclerView.Adapter<QuestionsAdopter.viewHolder> {
  ArrayList<QuestionsModel> list;
  Context context;

    public QuestionsAdopter(ArrayList<QuestionsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_multi_choice, parent,false);


        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       QuestionsModel model1=list.get(position);
       holder.editText.setText(model1.getText());

       holder.option1.setText(model1.getOption1());
       holder.option2.setText(model1.getOption2());
       holder.option3.setText(model1.getOption3());
       holder.option4.setText(model1.getOption4());

        holder.option1.setVisibility(View.VISIBLE);
        holder.option2.setVisibility(View.VISIBLE);
        holder.option3.setVisibility(View.VISIBLE);
        holder.option4.setVisibility(View.VISIBLE);

        if(model1.getOption1()==null ||model1.getOption1().equals(""))
            holder.option1.setVisibility(View.INVISIBLE);

        if(model1.getOption2()==null ||model1.getOption2().equals(""))
            holder.option2.setVisibility(View.INVISIBLE);

        if(model1.getOption3()==null ||model1.getOption3().equals(""))
            holder.option3.setVisibility(View.INVISIBLE);

        if(model1.getOption4()==null ||model1.getOption4().equals(""))
            holder.option4.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public  class  viewHolder extends RecyclerView.ViewHolder {
        TextView editText;
        RadioButton option1, option2, option3, option4;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
          editText=itemView.findViewById(R.id.question);
          option1=itemView.findViewById(R.id.optionA);
          option2=itemView.findViewById(R.id.optionB);
          option3=itemView.findViewById(R.id.optionC);
          option4=itemView.findViewById(R.id.optionD);



        }
    }
}
