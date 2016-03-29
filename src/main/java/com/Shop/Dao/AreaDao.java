package com.Shop.Dao;

import com.Shop.Model.Areas;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class AreaDao extends BaseDao implements IGeneralDao<Areas> {
    public Areas findById(int id) {
        return null;
    }

    public List<Areas> findAll() {
        Session session = super.openSession();
        String hql = "from Area";
        List<Areas> areases = session.createQuery(hql).list();
        return areases;
    }

    public void save(Areas areas) {
        super.hibernateTemplate.save(areas);
    }

    public void update(Areas areas) {
        super.hibernateTemplate.update(areas);
    }

    public void saveOrUpdate(Areas areas) {
        super.hibernateTemplate.saveOrUpdate(areas);
    }

    public void delete(Areas areas) {
        super.hibernateTemplate.delete(areas);
    }

    public void deleteById(int id) {
        Areas areas = findById(id);
        super.hibernateTemplate.delete(areas);
    }

    public void refresh(Areas areas) {

    }

    public void flush() {

    }
    public Areas findByName(String name){
        Session session = super.openSession();
        String hql = "from Area where name=:name";
        return (Areas) session.createQuery(hql).setParameter("name",name).uniqueResult();
    }
}
