package com.ese.model.dao;

import com.ese.model.db.MSLocationModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationDAO extends GenericDAO<MSLocationModel, Integer>{
}
