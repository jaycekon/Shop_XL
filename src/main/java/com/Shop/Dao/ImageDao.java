package com.Shop.Dao;

import com.Shop.Model.Image;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public class ImageDao extends BaseDao implements IGeneralDao<Image> {
    @Override
    public Image findById(int id) {
        return super.hibernateTemplate.get(Image.class,id);
    }

    @Override
    public List<Image> findAll() {
        String hql = "fomr Image";
        return super.openSession().createQuery(hql).list();
    }

    @Override
    public void save(Image image) {
        super.hibernateTemplate.save(image);
    }

    @Override
    public void update(Image image) {
        super.hibernateTemplate.update(image);
    }

    @Override
    public void saveOrUpdate(Image image) {

    }

    @Override
    public void delete(Image image) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(Image image) {

    }

    @Override
    public void flush() {

    }

    public List<Image> findAllByGoodId(int id){
        String hql ="from Image where good_Id =:id";
        return super.openSession().createQuery(hql).setParameter("id",id).list();
    }

    public List<Image> findAllByGoodIdAndStatus(int id,int status){
        String hql ="from Image where good_Id =:id and status =:status";
        return super.openSession().createQuery(hql).setParameter("id",id).setParameter("status",status).list();
    }

    public List<Image> findImage(){
        Session session = super.openSession();
        String hql ="from Image where good_Id = null ";
        List<Image> images = session.createQuery(hql).list();
        session.close();
        return images;
    }
}
