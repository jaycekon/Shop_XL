package com.Shop.Dao;

import com.Shop.Model.Orders;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class OrdersDao extends BaseDao implements  IGeneralDao<Orders> {
    @Override
    public Orders findById(int id) {
        return super.hibernateTemplate.get(Orders.class,id);
    }

    @Override
    public List<Orders> findAll() {
        Session session = super.openSession();
        String hql ="from Orders order by setTime desc";
        List<Orders> orders = session.createQuery(hql).list();
        session.close();
        return orders;
    }

    @Override
    public void save(Orders orders) {
        super.hibernateTemplate.save(orders);
    }

    @Override
    public void update(Orders orders) {
        super.hibernateTemplate.update(orders);
    }

    @Override
    public void saveOrUpdate(Orders orders) {
            super.hibernateTemplate.saveOrUpdate(orders);
    }

    @Override
    public void delete(Orders orders) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(Orders orders) {

    }

    @Override
    public void flush() {

    }

    public List<Orders> findAllByUser(int user_id){
        Session session = super.openSession();
        String hql ="from Orders where user_id=:user_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("user_id",user_id).list();
        session.close();
        return orders;
    }

    /**
     * 通过付款状态获取订单（后台）
     * @param f
     * @return
     */
    public List<Orders> findAllByF(int f) {
        Session session = super.openSession();
        String hql ="from Orders where f=:f and d=0 and p=0 order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("f",f).list();
        session.close();
        return orders;
    }

    /**
     * 通过付款状态以及用户ID获取订单(用户）
     * @param f
     * @param user_id
     * @return
     */
    public List<Orders> findAllByFAndUserId(int f,int user_id) {
        Session session = super.openSession();
        String hql ="from Orders where f=:f and d=0 and user_id=:user_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("f",f).setParameter("user_id",user_id).list();
        session.close();
        return orders;
    }

    /**
     * 通过配送状态获取订单（后台）
     * @param p
     * @return
     */
    public List<Orders> findAllByP(int p) {
        Session session = super.openSession();
        String hql ="from Orders where p=:p and f=1 and d=0 order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("p",p).list();
        session.close();
        return orders;
    }

    /**
     * 通过配送状态以及用户ID获取订单（用户）
     * @param p
     * @param user_id
     * @return
     */
    public List<Orders> findAllByPAndUserId(int p,int user_id) {
        Session session = super.openSession();
        String hql ="from Orders where  f=1 and p=:p and d=0 and user_id=:user_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("p",p).setParameter("user_id",user_id).list();
        session.close();
        return orders;
    }

    /**
     * 通过退货状态获取订单（后台）
     * @param t
     * @return
     */
    public List<Orders> findAllByT(int t) {
        Session session = super.openSession();
        String hql ="from Orders where t=:t and d=0 order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("t",t).list();
        session.close();
        return orders;
    }

    /**
     * 通过退货状态以及用户ID获取订单（用户）
     * @param t
     * @param user_id
     * @return
     */
    public List<Orders> findAllByTAndUserId(int t,int user_id) {
        Session session = super.openSession();
        String hql ="from Orders where t=:t and f=1 and c=0 and user_id=:user_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("t",t).setParameter("user_id",user_id).list();
        session.close();
        return orders;
    }

    /**
     * 通过评价状态获取订单
     * @param c
     * @return
     */

    public List<Orders> findAllByC(int c) {
        Session session = super.openSession();
        String hql ="from Orders where c=:c and d=1 order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("c",c).list();
        session.close();
        return orders;
    }

    /**
     * 用户通过评价状态与用户ID获取订单
     * @param c
     * @param user_id
     * @return
     */

    public List<Orders> findAllByCAndUserId(int c,int user_id) {
        Session session = super.openSession();
        String hql ="from Orders where c=:c and d=1 and user_id=:user_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("c",c).setParameter("user_id",user_id).list();
        session.close();
        return orders;
    }

    /**
     * 通过订单状态获取订单（后台）
     * @param d
     * @return
     */
    public List<Orders> findAllByD(int d) {
        Session session = super.openSession();
        String hql ="from Orders where d=:d order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("d",d).list();
        session.close();
        return orders;
    }

    /**
     * 通过订单状态以及用户ID获取订单
     * @param d
     * @param user_id
     * @return
     */
    public List<Orders> findAllByDAndUserId(int d,int user_id) {
        Session session = super.openSession();
        String hql ="from Orders where d=:d and user_id=:user_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("d",d).setParameter("user_id",user_id).list();
        session.close();
        return orders;
    }

    /**
     * 大区获取所有子级订单
     * @param area_id
     * @return
     */
    public List<Orders> findAllByAreaId(int area_id) {
        Session session = super.openSession();
        String hql ="from Orders where areas_id=:area_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("area_id",area_id).list();
        session.close();
        return orders;
    }


    /**
     * 角色获取所有子级订单
     * @param role_id
     * @return
     */
    public List<Orders> findAllByRoleId(int role_id) {
        Session session = super.openSession();
        String hql ="from Orders where roles_id=:role_id order by setTime desc";
        List<Orders> orders = session.createQuery(hql).setParameter("role_id",role_id).list();
        session.close();
        return orders;
    }



    public Orders findOrdersByUid(String uuid){
        Session session = super.openSession();
        String hql ="from Orders where uuid like:uuid order by setTime desc";
        Orders orders =(Orders)session.createQuery(hql).setParameter("uuid","%"+uuid+"%").uniqueResult();
        session.close();
        return orders;
    }


}
