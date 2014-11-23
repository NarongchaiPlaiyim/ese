package com.ese.utils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public enum Utils {
    ;
    public final static boolean TRUE = true;



    public static boolean isNull(final Object object){
        return object == null;
    }

    public static boolean isEmpty(final String string){
        return StringUtils.isEmpty(string);
    }

    public static boolean isZero(final String string){
        return 0 == string.length();
    }

    public static boolean equals(final String string, final String string2){
        return StringUtils.equals(string, string2);
    }

    public static<T> List<T> safetyList(final List<T> list) {
        return !isNull(list) && list.size() > 0 ? list : (List<T>) Collections.EMPTY_LIST;
    }

    public static<T> boolean isSafetyList(final List<T> list){
        return !isNull(list) && !isZero(list.size());
    }

    public static boolean isZero(int id){
        try {
            return id == 0;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static Date currentDate(){
       return Calendar.getInstance().getTime();
    }

    public static String getDocumentNo(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(currentDate());
    }
}
