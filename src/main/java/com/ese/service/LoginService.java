package com.ese.service;

import com.ese.model.dao.*;
import com.ese.model.db.BarcodeRegisterModel;
import com.ese.utils.Utils;
import com.ese.model.db.StaffModel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class LoginService extends Service{
    @Resource private StaffDAO staffDAO;
    @Resource private LocationDAO locationDAO;
    @Resource private WarehouseDAO warehouseDAO;
    @Resource private MSLocationItemsDAO locationItemsDAO;
    @Resource private BarcodeRegisterDAO barcodeRegisterDAO;

    @Getter StaffModel staffModel;

    public boolean isUserExist(final String userName, final String password){
        log.debug("-- isUserExist({}, {})", userName, password);
        boolean result = Utils.TRUE;
        try {
            staffModel = staffDAO.findByUserName(userName, password);
            if(Utils.isNull(staffModel)){
                result = !result;
            }
            return result;
        } catch (Exception e) {
            log.error("Exception while calling isUserExist()", e);
            return !result;
        }
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


    public void test(){
        try {

//            System.out.println(warehouseDAO.findByStatus2().toString()+"");
//            System.out.println(locationItemsDAO.findLocationByItemId(58));
//            System.out.println(locationDAO.getLocationModelList());
//            barcodeRegisterDAO.getDataTable();
            List<BarcodeRegisterModel> barcodeRegisterModelList = barcodeRegisterDAO.findByIsValid();
            System.out.println(barcodeRegisterModelList.toString());
            System.out.println(barcodeRegisterModelList.size());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
