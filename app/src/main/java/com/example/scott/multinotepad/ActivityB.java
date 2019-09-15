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
        getSupportActionBar().hide();
        setContentView(R.layout.b_activity);

    }


    @Override
    public void onBackPressed() {
        // Pressing the back arrow closes the current activity, returning us to the original activity
        finish();
//        Intent data = new Intent();
//        data.putExtra("USER_TEXT_IDENTIFIER", editText.getText().toString());
//        setResult(RESULT_OK, data);
//
//        super.onBackPressed();
    }
}
