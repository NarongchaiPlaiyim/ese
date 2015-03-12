package com.ese.internal.test;


import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.apache.commons.io.FilenameUtils;

import javax.faces.context.FacesContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        semester

//        academic year


        Calendar now = Calendar.getInstance();


        int year = now.get(Calendar.YEAR);
        System.out.println(year-2);
        System.out.println(year-1);
        System.out.println(year);
        System.out.println(year+1);

        List<Integer> academicYear = new ArrayList();
        academicYear.add(year);
        academicYear.add(year-1);
        academicYear.add(year+1);
        academicYear.add(year+2);

        List<String> semester = new ArrayList();
        semester.add("1");
        semester.add("2");
        semester.add("Summer");

        ArrayList<Integer> list = new ArrayList<Integer>(7);
    }


    private static String getOnlyDigit(String s) {
        StringBuilder result = new StringBuilder("");
        for(int i = 0; i < s.length(); i++) {
            try {
                result.append(Integer.parseInt(String.valueOf(s.charAt(i))));
            } catch (Exception e) {

            }
        }
        return result.toString();
    }

    public static final int getBinaryInt1() {
        return 0b101;
    }

    public static final int getBinaryInt2() {
        return 0B101;
    }

    public static final long getBinaryLong() {
        return 0b1010000101000101101000010100010110100001010001011010000101000101L;
    }

    public static final int[] getBinaryArray() {
        final int[] phases = {
                0b00110001,
                0b01100010,
                0b11000100,
                0b10001001,
                0b00010011,
                0b00100110,
                0b01001100,
                0b10011000};

        return phases;
    }

    public static  final int getInt1() {
        return 1_000_000;
    }

    public static  final int getInt2() {
        return 0______52;
    }

}
