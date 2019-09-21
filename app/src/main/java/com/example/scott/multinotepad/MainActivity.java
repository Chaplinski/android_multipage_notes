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
    private Note note = new Note();
    private String sTitlePassBack;
    private String sBodyPassBack;
    private String sDatePassBack;


    private TextView noteTitle;
    private TextView noteBody;
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

        Intent intent = getIntent();

        if (intent.hasExtra("Title Passback")) {
            displayNewListItem(intent);
        }

        this.loadFile();
//
////        Make some data - not always needed - used to fill list
//        int j = 0;
//        for (int i = 0; i < 20; i++) {
//            note.setTitle("here is a title");
//            note.setBody("The body is right here");
//            note.setDate("");
//            note.getDate();
//            noteList.add(note);
//            j++;
//        }

        setTitle("Multi Notes (#)");

    }

    private void displayNewListItem(Intent intent) {
        sTitlePassBack = intent.getStringExtra("Title Passback");
        sBodyPassBack = intent.getStringExtra("Body Passback");
        Note newNote = new Note();
        newNote.setTitle(sTitlePassBack);
        newNote.setBody(sBodyPassBack);
        newNote.setDate("");
        sDatePassBack = newNote.getDate();
        noteList.add(newNote);
    }

    private Note loadFile() {

//        Log.d(TAG, "loadFile: Loading JSON File");
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
            int iJsonRows = jsonObject.length();
            int iSavedObjects = iJsonRows/3;
            Log.d(TAG, "JSON number: " + iSavedObjects);

            for (int i = 0; i < iSavedObjects; i++) {
                String title = jsonObject.getString("title" + i);
                String body = jsonObject.getString("body" + i);
                String date = jsonObject.getString("date" + i);
                note.setTitle(title);
                note.setBody(body);
                note.setDate(date);
                noteList.add(note);
            }



        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }

    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
//        int pos = recyclerView.getChildLayoutPosition(v);
//        Note m = noteList.get(pos);
        TextView name = v.findViewById(R.id.name) ;
        String thisTitle = name. getText().toString();
        TextView body = v.findViewById(R.id.body) ;
        String thisBody = body. getText().toString();

        //Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();
        Intent intentNoteCreation = new Intent(MainActivity.this, ActivityNote.class);
        intentNoteCreation.putExtra("Note Title", thisTitle);
        intentNoteCreation.putExtra("Note Body", thisBody);
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
                //Toast.makeText(this, "You want to create a note", Toast.LENGTH_SHORT).show();
                Intent intentNoteCreation = new Intent(MainActivity.this, ActivityNote.class);
                intentNoteCreation.putExtra("Note Title", "Note title from other activity");
                intentNoteCreation.putExtra("Note Body", "Note BOOOOOOODY from other activity");
                startActivityForResult(intentNoteCreation, SAVE_NOTE_REQUEST_CODE);
                return true;
            case R.id.menuViewAppDetails:
                Toast.makeText(this, "You want to read about the app", Toast.LENGTH_SHORT).show();
                Intent intentAppDetails = new Intent(MainActivity.this, ActivityB.class);
                intentAppDetails.putExtra("Activity Title", "My Cool Activity");
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

    @Override
    protected void onPause() {
        saveNotes();
        super.onPause();
    }

    private void saveNotes() {
        Log.d(TAG, "saveNote: Saving JSON File");
        try {
            // read JSON file
            InputStream is = getApplicationContext().
                    openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            ArrayList<String> allData = new ArrayList<>();

//            iterate over JSON file
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {

                if(!line.contains("{") && !line.contains("}")) {

                    String result;
                    String line2 = line.substring(line.indexOf(": \"")+1);
                    String line3 = line2.substring(line2.indexOf("\"")+1);
                    lineNumber++;
//                    Log.d(TAG, "fromJSON 1: " + line);
//                    Log.d(TAG, "fromJSON 2: " + line2);
//                    Log.d(TAG, "fromJSON 3: " + line3);
                    //when removing quotes from date a different expression is needed
                    if (lineNumber % 3 == 0){
                        result = line3.split("\"")[0];
//                        Log.d(TAG, "fromJSON final: " + result);
                    } else {
                        result = line3.split("\",")[0];
//                        Log.d(TAG, "fromJSON final: " + result);
                    }
                    allData.add(result);
//                    Log.d(TAG, "fromJSON: " + result);
                }
            }
            Log.d(TAG, "fromJSON allData: " + allData);
            //now we have all of the data that was held in the JSON file in an ArrayList


            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();

            //iterate over ArrayList

            int noteNumber = 0;
            for (int i = 0; i < allData.size(); i++){
                if(i % 3 == 0){
                    writer.name("title" + noteNumber).value(allData.get(i));
                } else if (i % 3 == 1){
                    writer.name("body" + noteNumber).value(allData.get(i));
                } else {
                    writer.name("date" + noteNumber).value(sDatePassBack);
                    noteNumber++;
                }
            }

            writer.name("title" + noteNumber).value("writer title");
            writer.name("body" + noteNumber).value("writer body");
            writer.name("date" + noteNumber).value("writer date - most recently added");

            writer.endObject();
            writer.close();

//            StringWriter sw = new StringWriter();
//            writer = new JsonWriter(sw);
//            writer.setIndent("  ");
//            writer.beginObject();
//            writer.name("title").value(sTitlePassBack);
//            writer.name("body").value(sBodyPassBack);
//            writer.name("date").value(sDatePassBack);
//            writer.endObject();
//            writer.close();
//            Log.d(TAG, "saveProduct: JSON:\n" + sw.toString());


            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}