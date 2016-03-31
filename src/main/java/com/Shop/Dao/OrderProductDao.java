package com.Shop.Dao;

import com.Shop.Model.OrderProduct;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Repository
public class OrderProductDao extends BaseDao implements IGeneralDao<OrderProduct> {
    @Override
    public OrderProduct findById(int id) {
        return super.hibernateTemplate.get(OrderProduct.class,id);
    }

    @Override
    public List<OrderProduct> findAll() {
        Session session = super.openSession();
        String hql = "from OrderProduct";
        List<OrderProduct> orderProducts = session.createQuery(hql).list();
        return orderProducts;
    }

    @Override
    public void save(OrderProduct orderProduct) {
        super.hibernateTemplate.save(orderProduct);
    }

    @Override
    public void update(OrderProduct orderProduct) {
        super.hibernateTemplate.update(orderProduct);
    }

    @Override
    public void saveOrUpdate(OrderProduct orderProduct) {
        super.hibernateTemplate.saveOrUpdate(orderProduct);
    }

    @Override
    public void delete(OrderProduct orderProduct) {
        super.hibernateTemplate.delete(orderProduct);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(OrderProduct orderProduct) {

    }

    @Override
    public void flush() {

    }

    public List<OrderProduct> findAllByCartId(int id){
        String hql ="from OrderProduct where cart_id=:id";
        List<OrderProduct> orderProducts = super.openSession().createQuery(hql).setParameter("id",id).list();
        return orderProducts;
    }

    public List<OrderProduct> findAllByOrderId(int id){
        String hql ="from OrderProduct where orders_id=:id";
        List<OrderProduct> orderProducts = super.openSession().createQuery(hql).setParameter("id",id).list();
        return orderProducts;
    }
}
