package com.ese.beans;

import com.ese.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class main {
    public static void main(String[] args) {
        int ss = 55;
        int ff = 57;

        int s = 50;
        int f = 60;

        List<Integer> list = new ArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        list.add(3);
        list.add(6);
        list.add(9);

        System.out.println(list.toString());
        Map<String, String> newMap = new HashMap();

        for (Integer i : list){
            if (!newMap.containsKey(i+"")){
                newMap.put(i+"", i+"");
                //add to map
            } else {
                System.out.println(i);
                //delete
            }

        }

        System.out.println(newMap.toString());
    }


}
