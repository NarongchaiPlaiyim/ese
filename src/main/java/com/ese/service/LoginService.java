package com.ese.service;

import com.ese.model.dao.*;
import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.db.MenuObjectModel;
import com.ese.utils.Utils;
import com.ese.model.db.StaffModel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class LoginService extends Service{
    private static final long serialVersionUID = 4112578634088874840L;
    @Resource private StaffDAO staffDAO;
    @Resource private MenuObjectDAO menuObjectDAO;

    @Getter StaffModel staffModel;

    public boolean isUserExist(final String userName, final String password){
        log.debug("-- isUserExist({}, {})", userName, password);
        boolean result = Utils.TRUE;
        try {
            staffModel = staffDAO.findByUserNameAndPassword(userName, password);
            if(Utils.isNull(staffModel)){
                result = !result;
            }
            return result;
        } catch (Exception e) {
            log.error("Exception while calling isUserExist()", e);
            return !result;
        }
    }

    public Map<String,String> getAuthorize(){
        List<String> stringList;
        Map<String,String> map = new HashMap<String,String>();
        try {
            stringList = menuObjectDAO.findByStaffId(staffModel.getId());
            for (String s : stringList) map.put(s, s);
        } catch (Exception e) {
            System.err.println(e);
            log.error("Exception while calling getAuthorize()", e);
        }
        return map;
    }

    public List<StaffModel> getList(){
        try {
            return staffDAO.findAll();
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    public StaffModel getObject(){
        try{
            int i = 5;
            return staffDAO.findByID(i);
        } catch (Exception e){
            System.out.println("e :"+e);
            return new StaffModel();
        }
    }


    public void test(String startBarcode, String finishBarcode){
        try {

            System.out.println("test");
//            System.out.println(warehouseDAO.findByStatus2().toString()+"");
//            System.out.println(locationItemsDAO.findLocationByItemId(58));
//            System.out.println(locationDAO.getLocationModelList());
//            barcodeRegisterDAO.getDataTable();
//            List<BarcodeRegisterModel> barcodeRegisterModelList = barcodeRegisterDAO.findByIsValid();
//            System.out.println(barcodeRegisterModelList.toString());
//            System.out.println(barcodeRegisterModelList.size());
//            System.out.println("Price is "+barcodeRegisterDAO.getPrice("I-0000100"));

//            System.out.println(barcodeRegisterDAO.checkBarcode2(startBarcode, finishBarcode));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
