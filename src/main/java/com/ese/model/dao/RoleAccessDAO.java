package com.ese.model.dao;

import com.ese.model.db.RoleAccessModel;
import com.ese.model.view.report.RoleAccessViewReport;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleAccessDAO extends GenericDAO<RoleAccessModel, Integer>{

    public List<RoleAccessModel> findBySystemRoleId(int systemRoleId){
        List<RoleAccessModel> roleAccessModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("systemRoleModel.id", systemRoleId));
            criteria.add(Restrictions.eq("isValid", 1));
            roleAccessModels = Utils.safetyList(criteria.list());

        } catch (Exception e) {
            log.debug("Exception error findBySystemRoleId : ", e);
        }

        return roleAccessModels;
    }

    public List<RoleAccessModel> findByMenuObjectIdAndSystemRoleId(int menuObjId, int systemRoleId, String key){
        List<RoleAccessModel> roleAccessModels = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(RoleAccessModel.class, "ra");
            criteria.createAlias("ra.menuObjectModel", "mo");
            criteria.createAlias("ra.systemRoleModel", "so");

            if (!Utils.isZero(menuObjId)){
                criteria.add(Restrictions.eq("mo.id", menuObjId));
            }

            if (!Utils.isZero(systemRoleId)){
                criteria.add(Restrictions.eq("so.id", systemRoleId));
            }

            if (!Utils.isNull(key) && !Utils.isZero(key.trim().length())){
                Criterion objectCode = Restrictions.like("mo.code", "%" + key.trim() + "%");
                Criterion objecyName = Restrictions.like("mo.name", "%"+ key.trim() +"%");
                criteria.add(Restrictions.or(objectCode,objecyName));
            }

            criteria.add(Restrictions.eq("isValid", 1));
            roleAccessModels = Utils.safetyList(criteria.list());

        } catch (Exception e) {
            log.debug("Exception error findBySystemRoleId : ", e);
        }

        return roleAccessModels;
    }

    public List<RoleAccessViewReport> genSQLReportUserAndRole(){
        List<RoleAccessViewReport> reportViews = new ArrayList<RoleAccessViewReport>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".system_role.code AS ROLE_NAME, ");
        sqlBuilder.append(" ").append(getPrefix()).append(".menu_object.code AS OBJ_CODE, ");
        sqlBuilder.append(" ").append(getPrefix()).append(".menu_object.name AS OBJ_NAME ");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".role_access");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".system_role");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".role_access.system_role_id = ").append(getPrefix()).append(".system_role.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".menu_object");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".role_access.menu_object_id = ").append(getPrefix()).append(".menu_object.id");
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".system_role.code,");
        sqlBuilder.append(" ").append(getPrefix()).append(".menu_object.code,").append(getPrefix()).append(".menu_object.name");

        log.debug("--SQL {}",sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ROLE_NAME", StringType.INSTANCE)
                    .addScalar("OBJ_CODE", StringType.INSTANCE)
                    .addScalar("OBJ_NAME", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                RoleAccessViewReport report = new RoleAccessViewReport();
                report.setRoleName(Utils.parseString(entity[0], ""));
                report.setObjCode(Utils.parseString(entity[1], ""));
                report.setObjName(Utils.parseString(entity[2], ""));
                reportViews.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reportViews;
    }
}
