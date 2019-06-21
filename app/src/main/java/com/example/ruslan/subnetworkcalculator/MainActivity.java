package com.example.ruslan.subnetworkcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button btn_Submit;
    EditText txt_Ip;

    Spinner spinner;
    List<String> list;
    ArrayAdapter adapter;

    EditText txt_numOfNetworks;
    EditText txt_HostsPerNetwork;

    RadioGroup radioGroup;
    RadioButton rb_NON;
    RadioButton rb_HPN;

    Calculator calculator;

    boolean DataIsCorrect;

    int octet1;
    int octet2;
    int octet3;
    int octet4;

    int mask;

    int number_of_Networks = -1;
    int hosts_per_Network;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFields();

    }



    public void setupFields(){


        btn_Submit = (Button)findViewById(R.id.button_calculate);
        txt_Ip = (EditText)findViewById(R.id.txt_ip);
        spinner = (Spinner) findViewById(R.id.spinner);
        txt_numOfNetworks = (EditText)findViewById(R.id.txt_numOfNetworks);
        txt_HostsPerNetwork = (EditText)findViewById(R.id.txt_hostsPerNetwork);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rb_HPN = (RadioButton)findViewById(R.id.rb_HPN);
        rb_NON = (RadioButton)findViewById(R.id.rb_NON);



        fillSpiner();

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataIsCorrect = true;

                readIp();

                readMask();

                readPriority();


                if (DataIsCorrect) {
                    calculator = new Calculator(octet1, octet2, octet3, octet4, mask, number_of_Networks, hosts_per_Network);
                    calculator.calculate();

                    int size = calculator.allNetworks.size();

                    Log.d("MES ","SIZE IS ===== "+size);


                    if( size > 34 ){
                        showDealog();
                    }
                    else{
                        showText();
                    }
                }
            }
        });
    }

    public void fillSpiner(){

        list = new ArrayList<String>();
        int num1=255,num2=255,num3=255,num4=255;
        for(int i = 1;i < 31; i++){

            if(i > 0 && i <=8 ){
                num4 = (255 << i) & 255;
            }

            if(i > 8 && i <= 16){
                num4 = 0;
                num3 = (255 << (i-8)) & 255;
            }

            if(i > 16 && i <= 24){
                num4 = 0;
                num3 = 0;
                num2 = (255 << (i - 16)) & 255;
            }
                if(i > 24){
                num4 = 0;
                num3 = 0;
                num2 = 0;
                num1 = (255 << (i - 24)) & 255;
            }
            list.add(num1+"."+num2+"."+ num3 +"."+num4);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
    }

    public void showText(){

        String finalString = "";
        for (String s : calculator.allNetworks) {
            finalString+= s + "\n";
        }
        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        i.putExtra("allNetworks",finalString);
        startActivity(i);
    }
    public void showDealog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Number of networks is "+calculator.allNetworks.size()+", do you want to print them?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText();
            }
        });
        alert.setNegativeButton("No",null);
        alert.create().show();
    }

    public void readPriority(){

        int radioId = radioGroup.getCheckedRadioButtonId();

        if(radioId == rb_HPN.getId()){
            try{
                hosts_per_Network = Integer.parseInt(txt_HostsPerNetwork.getText().toString());
                number_of_Networks = -1;
            }catch (Exception e){
                e.printStackTrace();
                DataIsCorrect = false;
                Toast.makeText(getBaseContext(),"Wrong hosts per network number",Toast.LENGTH_LONG).show();
            }
        }
        else{
            try{
                hosts_per_Network = -1;
                number_of_Networks = Integer.parseInt(txt_numOfNetworks.getText().toString());
            }catch (Exception e){
                e.printStackTrace();
                DataIsCorrect = false;
                Toast.makeText(getBaseContext(),"Wrong number of network",Toast.LENGTH_LONG).show();
            }

        }

    }

    public void readMask(){
        try{
           mask = spinner.getSelectedItemPosition()+1;
        }catch (Exception e){
            e.printStackTrace();
            DataIsCorrect = false;
            Toast.makeText(getBaseContext(),"Wrong Mask",Toast.LENGTH_LONG).show();
        }
    }




    public void readIp(){
        try{
            String ip = txt_Ip.getText().toString();
            String[] subStr = ip.split("\\.");
            octet1 = Integer.parseInt(subStr[0]);
            octet2 = Integer.parseInt(subStr[1]);
            octet3 = Integer.parseInt(subStr[2]);
            octet4 = Integer.parseInt(subStr[3]);
            Log.d("ERROR","Length ====   "+subStr.length);

            if(subStr.length != 4 ||
                    octet1 < 0 && octet1 > 255 ||
                    octet2 < 0 && octet2 > 255 ||
                    octet3 < 0 && octet3 > 255 ||
                    octet4 < 0 && octet4 > 255){
                DataIsCorrect = false;
                Log.d("ERROR","Length ====   "+subStr.length);
                throw new Exception("Wrong IP");
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(getBaseContext(),"Wrong IP",Toast.LENGTH_LONG);
            toast.show();

        }

    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        if(radioId == rb_HPN.getId()){
            txt_numOfNetworks.setEnabled(false);
            txt_HostsPerNetwork.setEnabled(true);
        }
        else{
            txt_numOfNetworks.setEnabled(true);
            txt_HostsPerNetwork.setEnabled(false);
        }
    }
}
