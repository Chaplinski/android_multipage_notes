package com.example.scott.multinotepad;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Christopher on 1/30/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView body;
    public TextView dateTime;

    public MyViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.name);
        body = view.findViewById(R.id.body);
        dateTime = view.findViewById(R.id.dateTime);
    }

}
