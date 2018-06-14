package com.example.gradyle.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact");
}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
