package com.Shop.Dao;

import com.Shop.Model.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class RolesDao extends BaseDao implements IGeneralDao<Roles> {
    public Roles findById(int id) {
        return null;
    }

    public List<Roles> findAll() {
        return null;
    }

    public void save(Roles roles) {
        super.hibernateTemplate.save(roles);
    }

    public void update(Roles roles) {

    }

    public void saveOrUpdate(Roles roles) {

    }

    public void delete(Roles roles) {

    }

    public void deleteById(int id) {

    }

    public void refresh(Roles roles) {

    }

    public void flush() {

    }
}
