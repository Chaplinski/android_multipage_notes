package com.example.scott.multinotepad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ActivityNote extends AppCompatActivity {

    // Set parentActivityName in Manifest!!

    private TextView title;
    private TextView body;
    private String[] aValues = new String[5];
    private Note note = new Note();
    private static final String TAG = "ActivityNote";


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
                aValues[0] = title.getText().toString();
                aValues[1] = body.getText().toString();

                Toast.makeText(this, "You have saved your note", Toast.LENGTH_SHORT).show();
                Intent intentNoteCreation = new Intent(this, MainActivity.class);
                intentNoteCreation.putExtra("Note Array", aValues);

                startActivity(intentNoteCreation);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        note = loadFile();  // Load the JSON containing the product data - if it exists
        if (note != null) { // null means no file was loaded
            title.setText(note.getTitle());
            body.setText(note.getBody());
        }
        super.onResume();
    }

    private Note loadFile() {

        Log.d(TAG, "loadFile: Loading JSON File");
        note = new Note();
        try {
            InputStream is = getApplicationContext().
                    openFileInput(getString(R.string.file_name));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            String title = jsonObject.getString("title");
            String body = jsonObject.getString("body");
            String date = jsonObject.getString("date");
            note.setTitle(title);
            note.setBody(body);
            note.setDate(date);
            Log.d(TAG, "loadFile title: " + title);
            Log.d(TAG, "loadFile body: " + body);
            Log.d(TAG, "loadFile date: " + date);


        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }


    @Override
    protected void onPause() {
        note.setTitle(title.getText().toString());
        note.setBody(body.getText().toString());
        saveNote();

        super.onPause();
    }

    private void saveNote() {

        Log.d(TAG, "saveNote: Saving JSON File");
        Log.d(TAG, "saveNote title: " + note.getTitle());
        Log.d(TAG, "saveNote body: " + note.getBody());
        Log.d(TAG, "saveNote date: " + note.getDate());
        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("title").value(note.getTitle());
            writer.name("body").value(note.getBody());
            writer.name("date").value(note.getDate());
            writer.endObject();
            writer.close();


            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
