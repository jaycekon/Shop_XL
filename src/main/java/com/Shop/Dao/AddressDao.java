package com.Shop.Dao;

import com.Shop.Model.Address;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class AddressDao extends BaseDao implements IGeneralDao<Address> {
    @Override
    public Address findById(int id) {
        Session session = super.openSession();
        String hql = "from Address where id=:id";
        Address address =(Address)session.createQuery(hql).setParameter("id",id).uniqueResult();
        session.close();
        return address;
    }

    @Override
    public List<Address> findAll() {
        Session session = super.openSession();
        String hql = "from Address";
        List<Address> addresses = session.createQuery(hql).list();
        session.close();
        return addresses;
    }

    @Override
    public void save(Address address) {
        super.hibernateTemplate.save(address);
    }

    @Override
    public void update(Address address) {
        super.hibernateTemplate.update(address);
    }

    @Override
    public void saveOrUpdate(Address address) {

    }

    @Override
    public void delete(Address address) {
        super.hibernateTemplate.delete(address);
    }

    @Override
    public void deleteById(int id) {
        Address address = findById(id);
        super.hibernateTemplate.delete(address);
    }

    @Override
    public void refresh(Address address) {

    }

    @Override
    public void flush() {

    }

    public List<Address> findByUserId(int id){
        Session session =super.openSession();
        String hql="from Address where user_id=:id";
        List<Address> addresses = session.createQuery(hql).setParameter("id",id).list();
        session.close();
        return addresses;
    }

    public List<Address> findByUserIdAndFlag(int id){
        Session session =super.openSession();
        String hql="from Address where user_id=:id and flag =1";
        List<Address> addresses = session.createQuery(hql).setParameter("id",id).list();
        session.close();
        return addresses;
    }
}
