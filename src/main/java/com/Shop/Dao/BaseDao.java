package com.Shop.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class BaseDao {
    @Autowired
    protected HibernateTemplate hibernateTemplate;
    @Autowired
    protected SessionFactory sessionFactory;

    public SessionFactory getSessionFactory(){
        return this.sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public Session openSession(){
        return this.sessionFactory.openSession();
    }
}
