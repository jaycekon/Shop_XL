package com.Shop.Dao;

import com.Shop.Model.WithdrawalsOrder;
import com.Shop.Util.Page;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class WithdrawalsOrderDao extends BaseDao implements IGeneralDao<WithdrawalsOrder> {
    @Override
    public WithdrawalsOrder findById(int id) {
        return super.hibernateTemplate.get(WithdrawalsOrder.class,id);
    }

    @Override
    public List<WithdrawalsOrder> findAll() {
        Session session = super.openSession();
        String hql="from WithdrawalsOrder";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).list();
        session.close();
        return withdrawalsOrders;
    }

    @Override
    public void save(WithdrawalsOrder withdrawalsOrder) {
        super.hibernateTemplate.save(withdrawalsOrder);
    }

    @Override
    public void update(WithdrawalsOrder withdrawalsOrder) {

    }

    @Override
    public void saveOrUpdate(WithdrawalsOrder withdrawalsOrder) {

    }

    @Override
    public void delete(WithdrawalsOrder withdrawalsOrder) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(WithdrawalsOrder withdrawalsOrder) {

    }

    @Override
    public void flush() {

    }

    public List<WithdrawalsOrder> findAllByAreaId(int areas_id){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where areas_id =:areas_id order  by  date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setParameter("areas_id",areas_id).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByRoleId(int roles_id){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where roles_id =:roles_id order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setParameter("roles_id",roles_id).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByRole(){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where roles_id !=null order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByRoleAndPage(Page page){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where roles_id !=null order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByRoleAndStatus(int status){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where roles_id !=null and status=:status order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setParameter("status",status).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByRoleAndStatusAndPage(int status,Page page){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where roles_id !=null and status=:status order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setParameter("status",status)
                .setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByArea(){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where areas_id !=null order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByAreaAndPage(Page page){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where areas_id !=null order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setMaxResults(page.getEveryPage()).setFirstResult(page.getBeginIndex()).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByAreaAndStatus(int status){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where areas_id !=null and status=:status order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setParameter("status",status).list();
        session.close();
        return withdrawalsOrders;
    }

    public List<WithdrawalsOrder> findAllByAreaAndStatusAndPage(int status,Page page){
        Session session = super.openSession();
        String hql="from WithdrawalsOrder where areas_id !=null and status=:status order by date desc ";
        List<WithdrawalsOrder> withdrawalsOrders = session.createQuery(hql).setParameter("status",status)
                .setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        session.close();
        return withdrawalsOrders;
    }

}
