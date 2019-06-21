package com.example.ruslan.subnetworkcalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Calculator {



    List<String> allNetworks;

    int octet1 = 20;
    int octet2 = 20;
    int octet3 = 20;
    int octet4 = 0;

    int mask;

    int number_of_Networks = 16;
    int hosts_per_Network = 6;

    public Calculator(int octet1,int octet2,int octet3,int octet4, int mask,int non, int hpn){

        allNetworks = new ArrayList<String>();
        this.octet1 = octet1;
        this.octet2 = octet2;
        this.octet3 = octet3;
        this.octet4 = octet4;

        this.mask = mask;


        number_of_Networks = non;
        hosts_per_Network = hpn;


        if( number_of_Networks > 0) {
            hosts_per_Network = (int)(Math.pow(2, mask) / (number_of_Networks)) - 2;
        }else {
            number_of_Networks = (int)(Math.pow(2, mask) / (hosts_per_Network+2));
        }

    }

    public void calculate() {

        Log.d("IP", "IP Address  = " + octet1 + "."
                + octet2 + "." + octet3 + "." + octet4);

        Log.d("Mask", "Mask  = "+mask);

        Log.d("NON", "Non  = "+number_of_Networks);

        Log.d("HPN", "HPN  = "+hosts_per_Network);


        for (int i = 0; i < number_of_Networks-1; i++)
        {
            print(hosts_per_Network -1);
        }

        int rest = (int)(Math.pow(2,mask)-3 - (number_of_Networks-1)*(hosts_per_Network+2));
        if(rest > hosts_per_Network) {
            System.out.println("Last netwosk is bigger then other");
        }
        print(rest);
    }

    private void print(int inc) {
        String s="";
        s+= octet1 + "." + octet2 + "." + octet3 + "." + octet4;
        increase(1);
        s+="    \t\t"+octet1 + "." + octet2 + "." + octet3 + "." + octet4+"";
        increase(inc);
        s+="    \t\t"+ octet1 + "." + octet2 + "." + octet3 + "." + octet4+"";
        increase(1);
        s+="    \t\t"+ octet1 + "." + octet2 + "." + octet3 + "." + octet4;
        allNetworks.add(s);
        increase(1);
    }


    private void increase(int num) {
        octet4+=num;

        octet3+= octet4 / 256;
        octet4 = octet4 % 256;

        octet2+= octet3 / 256;
        octet3 = octet3 % 256;

        octet1+= octet2 / 256;
        octet2 = octet2 % 256;

    }

}
