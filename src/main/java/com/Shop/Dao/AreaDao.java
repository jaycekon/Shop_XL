package com.Shop.Dao;

import com.Shop.Model.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class AreaDao extends BaseDao implements IGeneralDao<Area> {
    public Area findById(int id) {
        return null;
    }

    public List<Area> findAll() {
        return null;
    }

    public void save(Area area) {
        super.hibernateTemplate.save(area);
    }

    public void update(Area area) {

    }

    public void saveOrUpdate(Area area) {

    }

    public void delete(Area area) {

    }

    public void deleteById(int id) {

    }

    public void refresh(Area area) {

    }

    public void flush() {

    }
}
