package com.Shop.Dao;

import com.Shop.Model.Good;
import com.Shop.Util.Page;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class GoodDao extends BaseDao implements IGeneralDao<Good> {
    public Good findById(int id) {
        return super.hibernateTemplate.get(Good.class,id);
    }

    public List<Good> findAll() {
        Session session = super.openSession();
        String hql ="from Good";
        List<Good> goods = session.createQuery(hql).list();
        session.close();
        return goods;
    }

    public void save(Good good) {
        super.hibernateTemplate.save(good);
    }

    public void update(Good good) {
        super.hibernateTemplate.update(good);
    }

    public void saveOrUpdate(Good good) {
        super.hibernateTemplate.saveOrUpdate(good);
    }

    public void delete(Good good) {
        super.hibernateTemplate.delete(good);
    }

    public void deleteById(int id) {
        super.hibernateTemplate.delete("id",id);
    }

    public void refresh(Good good) {

    }

    public void flush() {

    }

    public List<Good> findAllByName(String name){
        String hql ="from Good where name like:name";
        Session session = super.openSession();
        List<Good> goods = session.createQuery(hql).setParameter("name","%"+name+"%").list();
        return goods;
    }

    public List<Good> findAllByStatus(int status){
        String hql ="from Good where status=:status";
        Session session = super.openSession();
        List<Good> goods = session.createQuery(hql).setParameter("status",status).list();
        return goods;
    }
    public List<Good> findAllByStatus(int status,Page page){
        String hql ="from Good where status=:status";
        Session session = super.openSession();
        List<Good> goods = session.createQuery(hql).setParameter("status",status).setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        return goods;
    }

    public List<Good> findAllByPage(Page<Good> page) {
        Session session = super.openSession();
        String hql ="from Good";
        List<Good> goods = session.createQuery(hql).setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        session.close();
        return goods;
    }

}
