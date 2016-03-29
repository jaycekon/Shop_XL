package com.Shop.Dao;

import com.Shop.Model.Cart;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Repository
public class CartDao extends BaseDao {
    public Cart findByUserId(int id){
        String hql = "from Cart where user_id=:id";
        Cart cart =(Cart) super.openSession().createQuery(hql).setParameter("id",id).uniqueResult();
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
        super.hibernateTemplate.update(cart);
    }
}
