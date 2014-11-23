package com.ese.beans;

import com.ese.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        System.out.println(calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println(calendar.getTime());
        System.out.println("Current week of this year = " + calendar.get(Calendar.WEEK_OF_YEAR));


        System.out.println(Utils.currentDate());
//        System.out.println(Utils.getBatchNo());

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        System.out.println("Report Date: " + reportDate);

    }
}
