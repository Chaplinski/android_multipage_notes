package com.example.scott.multinotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityA extends AppCompatActivity {

    // Set parentActivityName in Manifest!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity);

        TextView textView = findViewById(R.id.activityLabel);

        Intent intent = getIntent();
        if (intent.hasExtra("Activity Title")) {
            String text = intent.getStringExtra("Activity Title");
            textView.setText("ActivityA\nOpened from " + text);
        }

    }
}
