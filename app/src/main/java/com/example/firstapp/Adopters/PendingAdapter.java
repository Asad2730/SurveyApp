package com.example.firstapp.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firstapp.Globals;
import com.example.firstapp.Models.Survey;
import com.example.firstapp.R;

import java.util.List;

public class PendingAdapter extends ArrayAdapter {
    private Context context;
    private List<Survey> list;
    private TextView title;

    public PendingAdapter(@NonNull Context context, List<Survey> list) {
        super(context, R.layout.pending_row,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.pending_row,parent,false);
        title = v.findViewById(R.id.title);
        title.setText(list.get(position).getTitle());
        return v;
    }

}
