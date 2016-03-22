package com.Shop.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public interface IGeneralDao<T> {

    public T findById(int id);

    public List<T> findAll();

    public void save(T t);

    public void update(T t);

    public void saveOrUpdate(T t);

    public void delete(T t);

    public void deleteById(int id);

    public void refresh(T t);

    public void flush();
}
