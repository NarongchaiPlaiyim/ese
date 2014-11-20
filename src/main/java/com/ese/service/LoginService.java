package com.ese.service;

import com.ese.utils.Utils;
import com.ese.model.StaffModel;
import com.ese.model.dao.StaffDAO;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class LoginService extends Service implements Serializable{
    @Resource private StaffDAO staffDAO;
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
}
