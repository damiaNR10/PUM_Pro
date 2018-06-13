package com.example.gradyle.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TemperatureConverter extends AppCompatActivity {

    Button bc_f;
    Button bf_c;
    Button bc_k;
    Button bk_c;
    Button bk_f;
    Button bf_k;
    EditText ETtemp;
    TextView TVres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_converter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bf_c = (Button) findViewById(R.id.bf_c);
        bc_f = (Button) findViewById(R.id.bc_f);
        bc_k = (Button) findViewById(R.id.bc_k);
        bk_c = (Button) findViewById(R.id.bk_c);
        bk_f = (Button) findViewById(R.id.bk_f);
        bf_k = (Button) findViewById(R.id.bf_k);
        ETtemp = (EditText) findViewById(R.id.ETtemp);
        TVres = (TextView) findViewById(R.id.TVres);

        bf_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String podanaWartosc = ETtemp.getText().toString();
                if (podanaWartosc.isEmpty())
                {
                    TVres.setText("Number needed");
                }else{
                    int do_zmiany = Integer.valueOf(podanaWartosc);
                    double wynik_koncowy = do_zmiany - 17.77778;

                    TVres.setText(String.valueOf(wynik_koncowy) + " °C");}
            }


        });

        bc_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String podanaWartosc = ETtemp.getText().toString();
                if (podanaWartosc.isEmpty())
                {
                    TVres.setText("Number needed");
                }else{
                    int do_zmiany = Integer.valueOf(podanaWartosc);
                    double wynik_koncowy = do_zmiany + 32;

                    TVres.setText(String.valueOf(wynik_koncowy) + " °F");}
            }


        });

        bc_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String podanaWartosc = ETtemp.getText().toString();
                if (podanaWartosc.isEmpty())
                {
                    TVres.setText("Number needed");
                }else{
                    int do_zmiany = Integer.valueOf(podanaWartosc);
                    double wynik_koncowy = do_zmiany + 273.15;

                    TVres.setText(String.valueOf(wynik_koncowy) + " °K");}
            }


        });

        bk_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String podanaWartosc = ETtemp.getText().toString();
                if (podanaWartosc.isEmpty())
                {
                    TVres.setText("Number needed");
                }else{
                    int do_zmiany = Integer.valueOf(podanaWartosc);
                    double wynik_koncowy = do_zmiany - 273.15;

                    TVres.setText(String.valueOf(wynik_koncowy) + " °C");}
            }


        });

        bk_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String podanaWartosc = ETtemp.getText().toString();
                if (podanaWartosc.isEmpty())
                {
                    TVres.setText("Number needed");
                }else{
                    int do_zmiany = Integer.valueOf(podanaWartosc);
                    double wynik_koncowy = do_zmiany * 9/5 - 459.67;

                    TVres.setText(String.valueOf(wynik_koncowy) + " °F");}
            }


        });

        bf_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String podanaWartosc = ETtemp.getText().toString();
                if (podanaWartosc.isEmpty())
                {
                    TVres.setText("Number needed");
                }else{
                    int do_zmiany = Integer.valueOf(podanaWartosc);
                    double wynik_koncowy = (do_zmiany + 459.67)* 5/9;

                    //String do_wysw = String.valueOf(wynik_koncowy) + " °K";

                    TVres.setText(String.valueOf(wynik_koncowy) + " °K");}
                //TVres.setText(new DecimalFormat("##.##").format(do_wysw));
            }


        });




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
