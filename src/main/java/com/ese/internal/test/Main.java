package com.ese.internal.test;


import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.apache.commons.io.FilenameUtils;

import javax.faces.context.FacesContext;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String input = "T0123dc4asd56";

//        if(input.charAt(0) == '(') {
//            System.out.println(getDigit(input));
//        }
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


}
