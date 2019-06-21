package com.example.ruslan.subnetworkcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {


    Button button_goToCalculator;
    Button button_help;
    Button button_about;
    Button button_close;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupFields();
    }

    public void setupFields(){
        button_goToCalculator = findViewById(R.id.button_calculate);
        button_goToCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ntent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(new Intent(MainMenuActivity.this,MainActivity.class));
            }
        });

        button_help = findViewById(R.id.button_help);
        button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this,help_.class));
            }
        });

        alert = new AlertDialog.Builder(this);
        button_about = findViewById((R.id.button_about));
        button_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    alert.setTitle("About");
                    alert.setMessage("This application is made for fast dividing network on subnetworks \nCreated by Valuiev Ruslan");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
            }
        });
    }
}
