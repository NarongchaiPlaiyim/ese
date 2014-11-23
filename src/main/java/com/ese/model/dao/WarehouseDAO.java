package com.ese.model.dao;

import com.ese.model.db.MSWarehouseModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseDAO extends GenericDAO<MSWarehouseModel, Integer>{
}
