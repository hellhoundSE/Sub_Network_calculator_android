package com.example.ruslan.subnetworkcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    Button btn_Back;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.txt_textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        setupFields();

        fillText();
    }

    public void fillText(){
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("allNetworks");
            textView.setText(value);
        }
    }

    public void setupFields() {
        btn_Back = (Button) findViewById(R.id.btn_back);

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });


    }



}
