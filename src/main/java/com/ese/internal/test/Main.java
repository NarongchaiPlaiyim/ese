package com.ese.internal.test;


import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.apache.commons.io.FilenameUtils;

import javax.faces.context.FacesContext;
import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println(getBinaryInt1());
        System.out.println(getBinaryInt2());
        System.out.println(getBinaryLong());
//        System.out.println(Arrays.deepToString(getBinaryArray()));
        System.out.println(getInt1());
        System.out.println(getInt2());
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
