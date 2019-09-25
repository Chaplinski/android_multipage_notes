package com.example.scott.multinotepad;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView nameTruncated;
    public TextView body;
    public TextView bodyTruncated;
    public TextView dateTime;

    public MyViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.name);
        nameTruncated = view.findViewById(R.id.nameTruncated);
        body = view.findViewById(R.id.body);
        bodyTruncated  = view.findViewById(R.id.bodyTruncated);
        dateTime = view.findViewById(R.id.dateTime);
    }

}
