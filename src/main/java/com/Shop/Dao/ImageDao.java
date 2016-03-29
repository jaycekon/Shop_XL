package com.Shop.Dao;

import com.Shop.Model.Image;

import java.util.List;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public class ImageDao extends BaseDao implements IGeneralDao<Image> {
    @Override
    public Image findById(int id) {
        return null;
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
}
