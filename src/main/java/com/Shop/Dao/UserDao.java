package com.Shop.Dao;

import com.Shop.Model.User;
import com.Shop.Util.Page;
import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */

public class UserDao extends BaseDao implements IGeneralDao<User>{


    public User findById(int id) {
        User user =super.hibernateTemplate.get(User.class,id);
        return user;
    }

    public List<User> findAll() {
        Session session = super.openSession();
        String hql = "from User";
        List<User> users = session.createQuery(hql).list();
        return users;
    }

    public List<User> findAllByRolesId(int roles_id) {
        Session session = super.openSession();
        String hql = "from User where roles_id=:roles_id";
        List<User> users = session.createQuery(hql).setParameter("roles_id",roles_id).list();
        session.close();
        return users;
    }

    public List<User> findAllByRolesId(int roles_id,Page page) {
        Session session = super.openSession();
        String hql = "from User where roles_id=:roles_id";
        List<User> users = session.createQuery(hql).setParameter("roles_id",roles_id).setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        session.close();
        return users;
    }

    public void save(User user) {
        super.hibernateTemplate.save(user);
    }

    public void update(User user) {
        super.hibernateTemplate.update(user);
    }

    public void saveOrUpdate(User user) {
        super.hibernateTemplate.saveOrUpdate(user);
    }

    public void delete(User user) {
        super.hibernateTemplate.delete(user);
    }

    public void deleteById(int id) {
        super.hibernateTemplate.delete("id",id);
    }

    public void refresh(User user) {

    }

    public void flush() {

    }

    public User findByName(String username){
        Session session = super.openSession();
        String hql = "from User where username=:name";
        return (User)session.createQuery(hql).setParameter("name",username).uniqueResult();
    }

    public User findByOpenId(String openId){
        Session session = super.openSession();
        String hql = "from User where openId=:openId";
        return (User)session.createQuery(hql).setParameter("openId",openId).uniqueResult();
    }


    public List<User> findAllByPage(Page page) {
        Session session = super.openSession();
        String hql = "from User";
        List<User> users = session.createQuery(hql).setFirstResult(page.getBeginIndex()).setMaxResults(page.getEveryPage()).list();
        return users;
    }


}
