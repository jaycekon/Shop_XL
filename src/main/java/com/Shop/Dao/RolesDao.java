package com.Shop.Dao;

import com.Shop.Model.Roles;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class RolesDao extends BaseDao implements IGeneralDao<Roles> {
    public Roles findById(int id) {
        return super.hibernateTemplate.get(Roles.class,id);
    }

    public List<Roles> findAll() {
        Session session = super.openSession();
        String hql = "from Roles";
        List<Roles> roles = session.createQuery(hql).list();
        return roles;
    }

    public List<Roles> findAllByAreasId(int areas_id) {
        Session session = super.openSession();
        String hql = "from Roles where areas_id=:areas_id";
        List<Roles> roles = session.createQuery(hql).setParameter("areas_id",areas_id).list();
        return roles;
    }

    public void save(Roles roles) {
        super.hibernateTemplate.save(roles);
    }

    public void update(Roles roles) {
        super.hibernateTemplate.update(roles);
    }

    public void saveOrUpdate(Roles roles) {
        super.hibernateTemplate.saveOrUpdate(roles);

    }

    public void delete(Roles roles) {
        super.hibernateTemplate.delete(roles);
    }

    public void deleteById(int id) {
        Roles roles = findById(id);
        super.hibernateTemplate.delete(roles);
    }

    public void refresh(Roles roles) {

    }

    public void flush() {

    }

    public Roles findByName(String name){
        Session session = super.openSession();
        String hql = "from Roles where name=:name";
        return (Roles) session.createQuery(hql).setParameter("name",name).uniqueResult();
    }

    public Roles findByOpenId(String openId){
        Session session = super.openSession();
        String hql = "from Roles where openId=:openId";
        return (Roles) session.createQuery(hql).setParameter("openId",openId).uniqueResult();
    }

}
