package com.example.scott.multinotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

    private static final int SAVE_NOTE_REQUEST_CODE = 101;
    private static final String TAG = "MainActivity";

    private final List<Employee> employeeList = new ArrayList<>();  // Main content is here

    private RecyclerView recyclerView; // Layout's recyclerview

    private EmployeesAdapter mAdapter; // Data to recyclerview adapter
//    private TextView userText;
//    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        userText = findViewById(R.id.userText);

    }

    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        int pos = recyclerView.getChildLayoutPosition(v);
        Employee m = employeeList.get(pos);

        Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();
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


}