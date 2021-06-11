package com.example.revilt_inf1e;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SecondActivity extends Activity{

    private Button button;
    private TextView popuptext;
    private Button add2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        popuptext = findViewById(R.id.textView);
        add2 = findViewById(R.id.btnOK);

        add2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // What we want to display when button is clicked
                    popuptext.setText("Het glas van Filtje 1 is leeg");
                    popuptext.setBackgroundColor(0xFFEAD017);
                    
                }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

    }


    public void openActivity(){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }


}


