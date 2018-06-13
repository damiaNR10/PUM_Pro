package com.example.gradyle.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DamianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damian);
        getSupportActionBar().setTitle("");
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
