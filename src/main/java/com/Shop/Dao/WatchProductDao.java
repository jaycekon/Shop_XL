package com.Shop.Dao;

import com.Shop.Model.WatchProduct;

import java.util.List;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public class WatchProductDao extends BaseDao implements IGeneralDao<WatchProduct> {
    @Override
    public WatchProduct findById(int id) {
        return null;
    }

    @Override
    public List<WatchProduct> findAll() {
        return null;
    }

    @Override
    public void save(WatchProduct watchProduct) {
        super.hibernateTemplate.save(watchProduct);
    }

    @Override
    public void update(WatchProduct watchProduct) {

    }

    @Override
    public void saveOrUpdate(WatchProduct watchProduct) {

    }

    @Override
    public void delete(WatchProduct watchProduct) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(WatchProduct watchProduct) {

    }

    @Override
    public void flush() {

    }

    public List<WatchProduct> findAllByUserId(int id) {
        String hql ="from WatchProduct where user_id =:id";
        List<WatchProduct> watchProducts = super.openSession().createQuery(hql).setParameter("id",id).list();
        return watchProducts;
    }

    public WatchProduct findByUIdAndPId(int user_id,int good_id) {
        String hql ="from WatchProduct where user_id =:u_id and good_id =:g_id";
        return (WatchProduct)super.openSession().createQuery(hql).setParameter("u_id",user_id).setParameter("g_id",good_id).uniqueResult();

    }
}
