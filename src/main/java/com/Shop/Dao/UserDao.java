package com.Shop.Dao;

import com.Shop.Model.User;
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
//        return (User)super.hibernateTemplate.get(User.class,username);
    }
}
