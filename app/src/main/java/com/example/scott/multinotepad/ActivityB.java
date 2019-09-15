package com.example.scott.multinotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityB extends AppCompatActivity {

    // Set parentActivityName in Manifest!!!

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity);

        TextView textView = findViewById(R.id.activityLabel);
        editText = findViewById(R.id.input);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText("ActivityB\nOpened from " + text);
        }

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
                Intent intentA = new Intent(ActivityB.this, ActivityA.class);
                intentA.putExtra("Activity Title", "My Cool Activity");
                startActivity(intentA);
                return true;
            case R.id.menuB:
                Toast.makeText(this, "You want to do B", Toast.LENGTH_SHORT).show();
                Intent intentB = new Intent(ActivityB.this, ActivityB.class);
                intentB.putExtra("Activity Title", "My Cool Activity");
                startActivity(intentB);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doneClicked(View v) {
        Intent data = new Intent(); // Used to hold results data to be returned to original activity
        data.putExtra("USER_TEXT_IDENTIFIER", editText.getText().toString());
        setResult(RESULT_OK, data);
        finish(); // This closes the current activity, returning us to the original activity
    }

    @Override
    public void onBackPressed() {
        // Pressing the back arrow closes the current activity, returning us to the original activity
        Intent data = new Intent();
        data.putExtra("USER_TEXT_IDENTIFIER", editText.getText().toString());
        setResult(RESULT_OK, data);


        super.onBackPressed();
    }
}
