package com.example.scott.multinotepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

    private static final int SAVE_NOTE_REQUEST_CODE = 101;
    private static final String TAG = "MainActivity";
    private final List<Note> noteList = new ArrayList<>();  // Main content is here
    private RecyclerView recyclerView; // Layout's recyclerview
    private NotesAdapter mAdapter; // Data to recyclerview adapter

//    private EditText title;
//    private EditText body;
//    private TextView userText;
//    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        title = findViewById(R.id.textTitle);
//        body = findViewById(R.id.textBody);

        recyclerView = findViewById(R.id.recycler);

        mAdapter = new NotesAdapter(noteList, this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Make some data - not always needed - used to fill list
        for (int i = 0; i < 20; i++) {
            Note note = new Note();
            note.setTitle("goofus name");
            note.setBody("Gallant body");
            noteList.add(note);
        }

    }

    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        int pos = recyclerView.getChildLayoutPosition(v);
        Note m = noteList.get(pos);

        //Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();
        Intent intentNoteCreation = new Intent(MainActivity.this, ActivityNote.class);
//        intentNoteCreation.putExtra("Note Title", "This is the title of the note");
//        intentNoteCreation.putExtra("Note Body", "This is the body of the note");
        startActivityForResult(intentNoteCreation, SAVE_NOTE_REQUEST_CODE);
        Log.d(TAG, "onActivityResult: User Text: " + SAVE_NOTE_REQUEST_CODE);
    }

    // From OnLongClickListener
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        // use this method to delete a note

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuCreateANote:
                Toast.makeText(this, "You want to create a note", Toast.LENGTH_SHORT).show();
                Intent intentNoteCreation = new Intent(MainActivity.this, ActivityNote.class);
//                intentA.putExtra("Activity Title", "My Cool Activity");
               startActivityForResult(intentNoteCreation, SAVE_NOTE_REQUEST_CODE);
                return true;
            case R.id.menuViewAppDetails:
                Toast.makeText(this, "You want to read about the app", Toast.LENGTH_SHORT).show();
                Intent intentAppDetails = new Intent(MainActivity.this, ActivityB.class);
//                intentB.putExtra("Activity Title", "My Cool Activity");
                startActivity(intentAppDetails);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SAVE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String text = data.getStringExtra("USER_TEXT_IDENTIFIER");
//                userText.setText(text);

                Log.d(TAG, "onActivityResult: User Text: " + text);
            } else {
                Log.d(TAG, "onActivityResult: result Code: " + resultCode);
            }

        } else {
            Log.d(TAG, "onActivityResult: Request Code " + requestCode);
        }
    }

//    @Override
//    protected void onPause() {
//        note.setTitle(title.getText().toString());
////        note.setBody(body.getText().toString());
//        saveNote();
//
//        super.onPause();
//    }
//
//    private void saveNote() {
//
//        Log.d(TAG, "saveNote: Saving JSON File");
//        try {
//            FileOutputStream fos = getApplicationContext().
//                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
//
//            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
//            writer.setIndent("  ");
//            writer.beginObject();
//            writer.name("title").value(note.getTitle());
//            writer.name("description").value(note.getBody());
//            writer.endObject();
//            writer.close();
//
//
//            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
//    }


}