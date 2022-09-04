package com.example.firstapp.Adopters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firstapp.Globals;
import com.example.firstapp.Models.Result;
import com.example.firstapp.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

public class ResultAdapter extends ArrayAdapter {
    private Context context;
    private TextView question, o1, o2, o3, o4, op1, op2, op3, op4, p_op1, p_op2, p_op3, p_op4;
    private PieChart pieChart;
    private List<Result> list;

    public ResultAdapter(@NonNull Context context, List<Result> list) {
        super(context, R.layout.analyize_results_row, list);
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View v, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.analyize_results_row, parent, false);

        String a1 = list.get(i).getO1();
        String a2 = list.get(i).getO2();
        String a3 = list.get(i).getO3();
        String a4 = list.get(i).getO4();
        String Q =  list.get(i).getQ();
        double p1 = round(list.get(i).getOp1());
        double p2 = round(list.get(i).getOp2());
        double p3 = round(list.get(i).getOp3());
        double p4 = round(list.get(i).getOp4());

        pieChart = v.findViewById(R.id.piechart);
        question = v.findViewById(R.id.question);


        o1 = v.findViewById(R.id.o1);
        o2 = v.findViewById(R.id.o2);
        o3 = v.findViewById(R.id.o3);
        o4 = v.findViewById(R.id.o4);

        op1 = v.findViewById(R.id.op1);
        op2 = v.findViewById(R.id.op2);
        op3 = v.findViewById(R.id.op3);
        op4 = v.findViewById(R.id.op4);

        p_op1 = v.findViewById(R.id.p_op1);
        p_op2 = v.findViewById(R.id.p_op2);
        p_op3 = v.findViewById(R.id.p_op3);
        p_op4 = v.findViewById(R.id.p_op4);

        o1.setText(a1);
        o2.setText(a2);
        o3.setText(a3);
        o4.setText(a4);

        question.setText(Q);
        op1.setText(a1);
        op2.setText(a2);
        op3.setText(a3);
        op4.setText(a4);

        p_op1.setText(p1+"%");
        p_op2.setText(p2+"%");
        p_op3.setText(p3+"%");
        p_op4.setText(p4+"%");

        pieChart.addPieSlice(
                new PieModel(
                        "o1",
                        (float) p1,
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "o2",
                        (float) p2,
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "o3",
                        (float) p3,
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "o4",
                        (float) p4,
                        Color.parseColor("#29B6F6")));

        pieChart.startAnimation();

        return v;
    }


    public static double round(double value) {

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
