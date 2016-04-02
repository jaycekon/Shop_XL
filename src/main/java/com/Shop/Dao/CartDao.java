package com.Shop.Dao;

import com.Shop.Model.Cart;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Repository
public class CartDao extends BaseDao {
    public Cart findByUserId(int id){
        Session session = super.openSession();
        String hql = "from Cart where user_id=:id";
        Cart cart =(Cart) session.createQuery(hql).setParameter("id",id).uniqueResult();
        session.close();
        return cart;
    }

    public void save(Cart cart){
        super.hibernateTemplate.save(cart);
    }

    public void delete(Cart cart){
        super.hibernateTemplate.delete(cart);
    }

    public Cart findByCartId(int id){
        return super.hibernateTemplate.get(Cart.class,id);
    }

    public void update(Cart cart){
        Session session = super.openSession();
        session.update(cart);
        session.beginTransaction().commit();
//       super.hibernateTemplate.update(cart);
    }
}
