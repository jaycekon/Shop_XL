package com.Shop.Dao;

import com.Shop.Model.Terrace;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class TerraceDao extends BaseDao implements IGeneralDao<Terrace> {
    public Terrace findById(int id) {
        return super.hibernateTemplate.get(Terrace.class,id);
    }

    public List<Terrace> findAll() {
//        Session session = super.openSession();
//        String hql = "from Terrace ";
//        List<Terrace> terraces = session.createQuery(hql).list();
//        session.close();
        return null;
    }

    public void save(Terrace terrace) {
        super.hibernateTemplate.save(terrace);
    }

    public void update(Terrace terrace) {
        super.hibernateTemplate.update(terrace);
    }

    public void saveOrUpdate(Terrace terrace) {
        super.hibernateTemplate.saveOrUpdate(terrace);
    }

    public void delete(Terrace terrace) {
        super.hibernateTemplate.delete(terrace);
    }

    public void deleteById(int id) {
        super.hibernateTemplate.delete("id",id);
    }

    public void refresh(Terrace terrace) {

    }

    public void flush() {

    }
}
