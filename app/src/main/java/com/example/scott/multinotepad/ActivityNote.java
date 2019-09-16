package com.example.scott.multinotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityNote extends AppCompatActivity {

    // Set parentActivityName in Manifest!!

    private TextView title;
    private TextView body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        TextView titleTextView = findViewById(R.id.textTitle);
        TextView bodyTextView = findViewById(R.id.textBody);
        title = findViewById(R.id.textTitle);
        body = findViewById(R.id.textBody);

        Intent intent = getIntent();
        if (intent.hasExtra("Note Title")) {
            String title = intent.getStringExtra("Note Title");
            titleTextView.setText(title);
            String body = intent.getStringExtra("Note Body");
            bodyTextView.setText(body);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveNote:
                Toast.makeText(this, "You have saved your note", Toast.LENGTH_SHORT).show();
                Intent intentNoteCreation = new Intent(this, MainActivity.class);
                intentNoteCreation.putExtra("Note Title", "Title coming from Note View");
                intentNoteCreation.putExtra("Note Body", "Body coming from Note View");

                startActivity(intentNoteCreation);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
