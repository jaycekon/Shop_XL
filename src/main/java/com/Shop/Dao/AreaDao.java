package com.Shop.Dao;

import com.Shop.Model.Area;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class AreaDao extends BaseDao implements IGeneralDao<Area> {
    @Override
    public Area findById(int id) {
        return super.hibernateTemplate.get(Area.class,id);
    }

    @Override
    public List<Area> findAll() {
        Session session = super.openSession();
        String hql ="from Area";
        List<Area> areas = session.createQuery(hql).list();
        session.close();
        return areas;
    }

    @Override
    public void save(Area area) {
        super.hibernateTemplate.save(area);
    }

    @Override
    public void update(Area area) {
        super.hibernateTemplate.update(area);
    }

    @Override
    public void saveOrUpdate(Area area) {
        super.hibernateTemplate.saveOrUpdate(area);
    }

    @Override
    public void delete(Area area) {
        super.hibernateTemplate.delete(area);
    }

    @Override
    public void deleteById(int id) {
        Area area = findById(id);
        super.hibernateTemplate.delete(area);
    }

    @Override
    public void refresh(Area area) {

    }

    @Override
    public void flush() {

    }

    public List<Area> findAllByAreaId(int area_id){
        Session session = super.openSession();
        String hql ="from Area where area_id =:area_id";
        List<Area> areas =session.createQuery(hql).setParameter("area_id",area_id).list();
        session.close();
        return areas;
    }

    public Area findAreaByAreaName(String name){
        Session session = super.openSession();
        String hql ="from Area where name =:name";
        Area areas =(Area) session.createQuery(hql).setParameter("name",name).uniqueResult();
        session.close();
        return areas;
    }

    public List<Area> findTopArea(){
        Session session = super.openSession();
        String hql ="from Area where area_id =null ";
        List<Area> areas =session.createQuery(hql).list();
        return areas;
    }
}
