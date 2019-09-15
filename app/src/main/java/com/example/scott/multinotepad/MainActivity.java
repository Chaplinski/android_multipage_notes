package com.example.scott.multinotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int B_REQUEST_CODE = 101;
    private static final String TAG = "MainActivity";
    private TextView userText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userText = findViewById(R.id.userText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuA:
                Toast.makeText(this, "You want to do A", Toast.LENGTH_SHORT).show();
                Intent intentA = new Intent(MainActivity.this, ActivityA.class);
                intentA.putExtra("Activity Title", "My Cool Activity");
                startActivity(intentA);
                return true;
            case R.id.menuB:
                Toast.makeText(this, "You want to do B", Toast.LENGTH_SHORT).show();
                Intent intentB = new Intent(MainActivity.this, ActivityB.class);
                intentB.putExtra("Activity Title", "My Cool Activity");
                startActivity(intentB);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doA(View v) {
        Intent intent = new Intent(MainActivity.this, ActivityA.class);
        intent.putExtra("Activity Title", "My Cool Activity");
        startActivity(intent);
    }

    public void doB(View v) {
        Intent intent = new Intent(MainActivity.this, ActivityB.class);
        intent.putExtra(Intent.EXTRA_TEXT, "Scooby Doo");
        startActivityForResult(intent, B_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == B_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String text = data.getStringExtra("USER_TEXT_IDENTIFIER");
                userText.setText(text);

                Log.d(TAG, "onActivityResult: User Text: " + text);
            } else {
                Log.d(TAG, "onActivityResult: result Code: " + resultCode);
            }

        } else {
            Log.d(TAG, "onActivityResult: Request Code " + requestCode);
        }
    }


}