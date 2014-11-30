package com.ese.beans;

import com.ese.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        int ss = 55;
        int ff = 57;

        int s = 50;
        int f = 60;

        for (int i = 0; i < 4; i++) {
            System.out.println(i);
            while (ss<=ff){
                System.out.println(ss);
                if(ss >= s && ss <= f){
                    System.out.println(ss);
                    return;
                }
                ss++;
            }
        }


        System.out.println("DONE");


//        for (int i = 0; i < 100; i++) {
//            if(i >= s && i <= f){
//                System.out.println(i);
//            }
//        }

    }


}
