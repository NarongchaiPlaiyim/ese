package com.ese.internal.test;


import com.ese.utils.FacesUtil;
import org.apache.commons.io.FilenameUtils;

import javax.faces.context.FacesContext;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        final String fileName = "PalletManagement.jrxml";
        System.out.println(new File(fileName).getAbsolutePath());
        System.out.println("sdf "+FilenameUtils.getPath("D:\\ese\\ESE's Project\\ese\\PalletManagement.jrxml"));
        String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
        System.out.println(fileNameWithOutExt);

        System.out.println(FilenameUtils.getBaseName(fileName));
        System.out.println(FilenameUtils.getFullPath(fileName));

//        FacesUtil.getExternalContext().getRealPath("/");

        File f = null;
        String path = "";
        boolean bool = false;

        try{
            // create new files
            f = new File("PalletManagement.jrxml");

            // returns true if the file exists
            bool = f.exists();

            // if file exists
            if(bool) {
                // get absolute path
                path = f.getAbsolutePath();

                // prints
                System.out.print("Absolute Pathname "+ path);
            }
        }catch(Exception e){
            // if any error occurs
            e.printStackTrace();
            System.err.println(e);
        }
    }


}
