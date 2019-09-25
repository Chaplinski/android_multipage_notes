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

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.name.setText(note.getTitle());
        String cutName = stringCutToEighty(note.getTitle());
        holder.nameTruncated.setText(cutName);
        holder.body.setText(note.getBody());
        String cutBody = stringCutToEighty(note.getBody());
        holder.bodyTruncated.setText(cutBody);
        holder.dateTime.setText(note.getDate());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void removeItem(int position) {
        noteList.remove(position);
        notifyItemRemoved(position);
    }

    private String stringCutToEighty(String incoming){
        if (incoming.length() > 80) {
            String outgoing = incoming.substring(0, 80);
            outgoing = outgoing + "...";
            return outgoing;
        }
        return incoming;
    }
}