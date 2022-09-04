package com.example.firstapp.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firstapp.Globals;
import com.example.firstapp.Models.FacultyModel;
import com.example.firstapp.R;

import java.util.List;

public class FacultyAdapter extends ArrayAdapter {
    private List<FacultyModel> list;
    private Context context;
    private CheckBox chk;

    public FacultyAdapter(@NonNull Context context, List<FacultyModel> list) {
        super(context, R.layout.faculty_populate_row,list);
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.faculty_populate_row,parent,false);
        chk = v.findViewById(R.id.name);
        chk.setText(list.get(position).getName());
        chk.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = list.get(position).getId();
                if(b){
                    if(!Globals.facultyIds.contains(id)){
                        Globals.facultyIds.add(id);
                    }
                }
                else{
                    Globals.facultyIds.remove(id);
                }
            }
        });

        return v;
    }
}
