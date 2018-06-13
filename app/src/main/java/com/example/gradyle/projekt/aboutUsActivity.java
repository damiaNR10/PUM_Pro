package com.example.gradyle.projekt;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.lucasurbas.listitemview.ListItemView;

public class aboutUsActivity extends AppCompatActivity {

    String items[] = new String[] {"Patryk Kudła", "Damian Róg", "Sebastian Trzuskot"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position==0) {
                    Intent myintent = new Intent(view.getContext(),InformationActivity.class);
                    startActivityForResult(myintent,0);
                }
                if(position==1) {
                    Intent myintent = new Intent(view.getContext(), DamianActivity.class);
                    startActivityForResult(myintent, 0);
                }
                if(position==2) {
                    Intent myintent = new Intent(view.getContext(), SebastianActivity.class);
                    startActivityForResult(myintent, 0);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
