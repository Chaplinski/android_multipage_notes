package com.example.scott.multinotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

public class ActivityNote extends AppCompatActivity {

    // Set parentActivityName in Manifest!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        TextView textView = findViewById(R.id.textView1);

        Intent intent = getIntent();
        if (intent.hasExtra("Activity Title")) {
            String text = intent.getStringExtra("Activity Title");
            textView.setText("ActivityNote\nOpened from " + text);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_activity_menu, menu);
        return true;
    }
}
