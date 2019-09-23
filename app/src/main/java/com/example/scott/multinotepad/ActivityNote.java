package com.example.scott.multinotepad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static android.text.TextUtils.isEmpty;

public class ActivityNote extends AppCompatActivity {

    // Set parentActivityName in Manifest!!

    private EditText title;
    private EditText body;
    private Note note = new Note();
    private static final String TAG = "ActivityNote";
    private String sIncomingTitle = "";
    private String sIncomingBody = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        title = findViewById(R.id.textTitle);
        body = findViewById(R.id.textBody);

        Intent intent = getIntent();
        if (intent.hasExtra("Note Title")) {
            Toast.makeText(this, "I am in the note title", Toast.LENGTH_SHORT).show();
            sIncomingTitle = intent.getStringExtra("Note Title");
            Log.d(TAG, "noteTitle: " + sIncomingTitle);
            title.setText(sIncomingTitle);
            sIncomingBody = intent.getStringExtra("Note Body");
            body.setText(sIncomingBody);
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
                Intent intentNoteCreation = new Intent(this, MainActivity.class);
                String sTitleText = title.getText().toString();
                String sBodyText = body.getText().toString();
                Log.d(TAG, "onOptionsItemSelected: " + sTitleText);

                if((sIncomingTitle.compareTo(sTitleText) != 0) || (sIncomingBody.compareTo(sBodyText) != 0)) {
                    if (!sTitleText.isEmpty()) {
                    //if the title is blank then do not save note

                        String sDate = note.getCurrentDate();
//                    Toast.makeText(this, "You have saved your note", Toast.LENGTH_SHORT).show();
                        intentNoteCreation.putExtra("Title Passback", sTitleText);
                        intentNoteCreation.putExtra("Body Passback", sBodyText);
                        intentNoteCreation.putExtra("Date Passback", sDate);
                    } else {
                        Toast.makeText(this, "Untitled notes are not saved", Toast.LENGTH_SHORT).show();
                    }
                }
                startActivity(intentNoteCreation);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }



    @Override
    protected void onPause() {
//        note.setTitle(title.getText().toString());
//        note.setBody(body.getText().toString());
        //saveNote();

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        final String sTitleText = title.getText().toString();
        final String sBodyText = body.getText().toString();
        if((sIncomingTitle.compareTo(sTitleText) != 0) || (sIncomingBody.compareTo(sBodyText) != 0)) {

            if (sTitleText.length() > 0) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Your note is not saved!")
                        .setMessage("Save note \"" + sTitleText + "\"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNoteCreation = new Intent(ActivityNote.this, MainActivity.class);
                                String sDate = note.getCurrentDate();
                                intentNoteCreation.putExtra("Title Passback", sTitleText);
                                intentNoteCreation.putExtra("Body Passback", sBodyText);
                                intentNoteCreation.putExtra("Date Passback", sDate);
                                startActivity(intentNoteCreation);
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .show();
            } else {
                super.onBackPressed();
            }
        }
    }
}
