package com.Shop.Dao;

import com.Shop.Model.Logistic;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
public class LogisticDao extends BaseDao implements IGeneralDao<Logistic> {
    @Override
    public Logistic findById(int id) {
        return null;
    }

    @Override
    public List<Logistic> findAll() {
        Session session =super.openSession();
        String hql ="from Logistic ";
        List<Logistic> logistics = session.createQuery(hql).list();
        session.close();
        return logistics;
    }

    @Override
    public void save(Logistic logistic) {
        super.hibernateTemplate.save(logistic);
    }

    @Override
    public void update(Logistic logistic) {
        super.hibernateTemplate.update(logistic);
    }

    @Override
    public void saveOrUpdate(Logistic logistic) {

    }

    @Override
    public void delete(Logistic logistic) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(Logistic logistic) {

    }

    @Override
    public void flush() {

    }


    public Logistic findLogisticByName(String name){
        Session session  =super.openSession();
        String hql ="from Logistic where logis_comp_name=:name";
        Logistic logistic  =(Logistic)session.createQuery(hql).setParameter("name",name).uniqueResult();
        session.close();
        return logistic;
    }
}
