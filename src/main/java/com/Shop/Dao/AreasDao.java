package com.Shop.Dao;

import com.Shop.Model.Areas;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Repository
public class AreasDao extends BaseDao implements IGeneralDao<Areas> {
    public Areas findById(int id) {
        return super.hibernateTemplate.get(Areas.class,id);
    }

    public List<Areas> findAll() {
        Session session = super.openSession();
        String hql = "from Areas";
        List<Areas> areases = session.createQuery(hql).list();
        return areases;
    }

    public void save(Areas areas) {
        super.hibernateTemplate.save(areas);
    }

    public void update(Areas areas) {
        super.hibernateTemplate.update(areas);
    }

    public void saveOrUpdate(Areas areas) {
        super.hibernateTemplate.saveOrUpdate(areas);
    }

    public void delete(Areas areas) {
        super.hibernateTemplate.delete(areas);
    }

    public void deleteById(int id) {
        Areas areas = findById(id);
        super.hibernateTemplate.delete(areas);
    }

    public void refresh(Areas areas) {

    }

    public void flush() {

    }
    public Areas findByOpenId(String openId){
        Session session = super.openSession();
        String hql = "from Areas where openId=:openId";
        return (Areas) session.createQuery(hql).setParameter("openId",openId).uniqueResult();
    }

    public Areas findByFlag(long flag){
        Session session = super.openSession();
        String hql = "from Areas where flag=:flag";
        return (Areas) session.createQuery(hql).setParameter("flag",flag).uniqueResult();
    }
}
