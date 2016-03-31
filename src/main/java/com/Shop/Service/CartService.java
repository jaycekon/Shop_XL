package com.Shop.Service;

import com.Shop.Dao.CartDao;
import com.Shop.Model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Service
public class CartService {
    @Autowired
    private CartDao cartDao;

    public void addCart(Cart cart){
        cartDao.save(cart);
    }

    public Cart getCartByUserId(int id){
        return cartDao.findByUserId(id);
    }

    public Cart getByCartId(int id){
        return cartDao.findByCartId(id);
    }

    public void deleteCart(Cart cart){
        cartDao.delete(cart);
    }

    public void updateCart(Cart cart){
        cartDao.update(cart);
    }


}
