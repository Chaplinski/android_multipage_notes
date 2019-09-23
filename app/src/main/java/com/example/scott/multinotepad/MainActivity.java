package com.example.scott.multinotepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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
    private boolean jsonFilePresent = false;
    private boolean hasBeenAdded = false;

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

        setTitle("Multi Notes (" + noteList.size() + ")");

    }

    private void displayNewListItem(Intent intent) {
        sTitlePassBack = intent.getStringExtra("Title Passback");
        sBodyPassBack = intent.getStringExtra("Body Passback");
        sDatePassBack = intent.getStringExtra("Date Passback");
        String sOriginalTitle = intent.getStringExtra("Original Title");
        Log.d(TAG, "displayNewListItem: " + sOriginalTitle);
        if (!sOriginalTitle.isEmpty()){
            Log.d(TAG, "displayNewListItem: in the if");

            jsonFilePresent = true;
            saveNotes(true, sOriginalTitle);

        } else {
            Log.d(TAG, "displayNewListItem: in the else");

            Note newNote = new Note();
            newNote.setTitle(sTitlePassBack);
            newNote.setBody(sBodyPassBack);
            String actualDate = newNote.getDateCurrentTimeZone(sDatePassBack);
            newNote.setDate(actualDate);
            noteList.add(newNote);
        }

    }

    private void loadFile() {


//        Log.d(TAG, "loadFile: Loading JSON File");
        try {
            InputStream is = getApplicationContext().
                    openFileInput(getString(R.string.file_name));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.d(TAG, "loadFile: " + sb);

            JSONObject jsonObject = new JSONObject(sb.toString());
            int iJsonRows = jsonObject.length();
            int iSavedObjects = iJsonRows/3;
            Log.d(TAG, "JSON number: " + iSavedObjects);

            for (int i = 0; i < iSavedObjects; i++) {
                Note note = new Note();
                String title = jsonObject.getString("title" + i);
                Log.d(TAG, "STRING title:" + i + " " + title);
                String body = jsonObject.getString("body" + i);
                Log.d(TAG, "STRING body:" + i + " " + body);
                String date = jsonObject.getString("date" + i);
                Log.d(TAG, "STRING date:" + i + " " + date);

                note.setTitle(title);
                note.setBody(body);
                String actualTime = note.getDateCurrentTimeZone(date);
                note.setDate(actualTime);
                noteList.add(note);
            }
            //saveNotes() behaves differently whether there already is a JSON file or not
            jsonFilePresent = true;

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
//        int pos = recyclerView.getChildLayoutPosition(v);
//        Note m = noteList.get(pos);
        TextView name = v.findViewById(R.id.name);
        String thisTitle = name.getText().toString();
        TextView body = v.findViewById(R.id.body) ;
        String thisBody = body.getText().toString();

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
        TextView name = v.findViewById(R.id.name);
        String thisTitle = name.getText().toString();
        Toast.makeText(this, thisTitle, Toast.LENGTH_SHORT).show();
        saveNotes(true, thisTitle);
        mAdapter.removeItem(0);
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
                Intent intentNoteCreation = new Intent(MainActivity.this, ActivityNote.class);
                startActivityForResult(intentNoteCreation, SAVE_NOTE_REQUEST_CODE);
                return true;
            case R.id.menuViewAppDetails:
                //Toast.makeText(this, "You want to read about the app", Toast.LENGTH_SHORT).show();
                Intent intentAppDetails = new Intent(MainActivity.this, ActivityB.class);
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
        saveNotes(false, "");
        super.onPause();
    }

    private void saveNotes(boolean deleteFile, String thisTitle) {
        Log.d(TAG, "saveNote: Saving JSON File");
        try {
            ArrayList<String> allData = new ArrayList<>();
            // read JSON file
            if (jsonFilePresent) {
                Log.d(TAG, "saveNotes: JSON file present");
                InputStream is = getApplicationContext().
                        openFileInput(getString(R.string.file_name));
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));


//            iterate over JSON file
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {

                    if (!line.contains("{") && !line.contains("}")) {

                        String result;
                        String line2 = line.substring(line.indexOf(": \"") + 1);
                        String line3 = line2.substring(line2.indexOf("\"") + 1);
                        lineNumber++;
                    Log.d(TAG, "fromJSON 1: " + line);
                    Log.d(TAG, "fromJSON 2: " + line2);
                    Log.d(TAG, "fromJSON 3: " + line3);
                        //when removing quotes from date a different expression is needed
                        if (lineNumber % 3 == 0) {
                            result = line3.split("\"")[0];
                        } else {
                            result = line3.split("\",")[0];
                        }
                        allData.add(result);
                    Log.d(TAG, "fromJSON: " + result);
                    }
                }
                Log.d(TAG, "fromJSON allData before deletion: " + allData);

                if(deleteFile){
                    int indexOfNoteToDelete = allData.indexOf(thisTitle);
                    Log.d(TAG, "fromJSON allData deleted date: " + allData.get(indexOfNoteToDelete +2));
                    Log.d(TAG, "fromJSON allData deleted body: " + allData.get(indexOfNoteToDelete +1));
                    Log.d(TAG, "fromJSON allData deleted title: " + allData.get(indexOfNoteToDelete));
                    allData.remove(indexOfNoteToDelete + 2);
                    allData.remove(indexOfNoteToDelete + 1);
                    allData.remove(indexOfNoteToDelete);
                }
                Log.d(TAG, "fromJSON allData after deletion: " + allData);
            } else {
                Log.d(TAG, "saveNotes: JSON file NOT");
            }
            
            //now we have all of the data that was held in the JSON file in an ArrayList


            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            int noteNumber = 1;
            if ((sTitlePassBack != null) && (!hasBeenAdded)) {
                Log.d(TAG, "passBack: " + sTitlePassBack);
                writer.name("title0").value(sTitlePassBack);
                if (!sBodyPassBack.isEmpty()) {
                    writer.name("body0").value(sBodyPassBack);
                } else {
                    writer.name("body0").value(" ");
                }
                writer.name("date0").value(sDatePassBack);
                hasBeenAdded = true;
            } else {
                noteNumber = 0;
            }

            //iterate over ArrayList
            if (jsonFilePresent) {
                for (int i = 0; i < allData.size(); i++) {
                    if (i % 3 == 0) {
                        writer.name("title" + noteNumber).value(allData.get(i));
                    } else if (i % 3 == 1) {
                        writer.name("body" + noteNumber).value(allData.get(i));
                    } else {
                        writer.name("date" + noteNumber).value(allData.get(i));
                        noteNumber++;
                    }
                }
            }

            writer.endObject();
            writer.close();

            //Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private String stringCutToEighty(String incoming){
        Log.d(TAG, "stringCutToEighty: string length - " + incoming.length());

        if (incoming.length() > 80) {
            Log.d(TAG, "stringCutToEighty: incoming - " + incoming);
            String outgoing = incoming.substring(0, 80);
            Log.d(TAG, "stringCutToEighty: ougoing - " + outgoing);
            outgoing = outgoing + "...";
            return outgoing;
        }
        return incoming;
    }

}