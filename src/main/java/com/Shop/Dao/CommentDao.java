package com.Shop.Dao;

import com.Shop.Model.Comment;
import com.Shop.Model.Good;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 2016/4/21 0021.
 */
public class CommentDao extends BaseDao implements IGeneralDao<Comment> {

    @Override
    public Comment findById(int id) {
        return null;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public void save(Comment comment) {
        super.hibernateTemplate.save(comment);
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void saveOrUpdate(Comment comment) {

    }

    @Override
    public void delete(Comment comment) {
        super.hibernateTemplate.save(comment);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void refresh(Comment comment) {

    }

    @Override
    public void flush() {

    }

    public List<Comment> findCommentByGoodId(int good_id){
        Session session = super.openSession();
        String hql = "from Comment where good_id=:good_id";
        List<Comment>  comments = session.createQuery(hql).setParameter("good_id","good_id").list();
        session.close();
        return comments;

    }
}
