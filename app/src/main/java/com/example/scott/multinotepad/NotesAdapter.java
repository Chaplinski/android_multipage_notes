package com.example.scott.multinotepad;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "NotesAdapter";
    private List<Note> noteList;
    private MainActivity mainAct;

    public NotesAdapter(List<Note> noteList, MainActivity ma) {
        this.noteList = noteList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Note " + position);

        Note employee = noteList.get(position);

        holder.name.setText(employee.getName());
        holder.department.setText(employee.getDepartment());
        holder.dateTime.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}