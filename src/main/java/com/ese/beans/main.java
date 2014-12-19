package com.ese.beans;

import com.ese.service.security.encryption.EncryptionService;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(EncryptionService.encryption("admin"));
        //21232f297a57a5a743894a0e4a801fc3
        //21232f297a57a5a743894a0e4a801fc3
//        1	1	1	administrator	21232f297a57a5a743894a0e4a801fc3	admin	administrator							USER

        //0cc175b9c0f1b6a831c399e269772661
        //0cc175b9c0f1b6a831c399e269772661
        //0000000co5qrjg7hmqk33gsps9kne9j1

        List<String> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(""+i);
        }
        System.out.println(list.toString());
        System.out.println(list.contains("5"));
        System.out.println(list.indexOf("5"));


        Map<String,String> map = new HashMap<String,String>();
        for (String s : list) map.put(s, s);
        System.out.println(map.toString());


    }


}
