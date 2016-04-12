package com.Shop.Dao;

import com.Shop.Model.CountOrder;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
@Repository
public class CountOrderDao extends BaseDao implements IGeneralDao<CountOrder> {
    @Override
    public CountOrder findById(int id) {
        return super.hibernateTemplate.get(CountOrder.class,id);
    }

    @Override
    public List<CountOrder> findAll() {
        Session session = super.openSession();
        String hql ="from CountOrder";
        List<CountOrder> countOrders =session.createQuery(hql).list();
        session.close();
        return countOrders;
    }

    public List<CountOrder> findAllByUserId(int user_id) {
        Session session = super.openSession();
        String hql ="from CountOrder where user_id=:user_id order by date desc";
        List<CountOrder> countOrders =session.createQuery(hql).setParameter("user_id",user_id).list();
        session.close();
        return countOrders;
    }



    @Override
    public void save(CountOrder countOrder) {
        super.hibernateTemplate.save(countOrder);
    }

    @Override
    public void update(CountOrder countOrder) {

    }

    @Override
    public void saveOrUpdate(CountOrder countOrder) {

    }

    @Override
    public void delete(CountOrder countOrder) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(CountOrder countOrder) {

    }

    @Override
    public void flush() {

    }
}
