package com.Shop.Dao;

import com.Shop.Model.ExitOrders;

import java.util.List;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class ExitOrdersDao extends BaseDao implements IGeneralDao<ExitOrders> {
    @Override
    public ExitOrders findById(int id) {
        return super.hibernateTemplate.get(ExitOrders.class,id);
    }

    @Override
    public List<ExitOrders> findAll() {
        return null;
    }

    @Override
    public void save(ExitOrders exitOrders) {
        super.hibernateTemplate.save(exitOrders);
    }

    @Override
    public void update(ExitOrders exitOrders) {
        super.hibernateTemplate.update(exitOrders);
    }

    @Override
    public void saveOrUpdate(ExitOrders exitOrders) {

    }

    @Override
    public void delete(ExitOrders exitOrders) {
        super.hibernateTemplate.delete(exitOrders);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(ExitOrders exitOrders) {

    }

    @Override
    public void flush() {

    }

}
