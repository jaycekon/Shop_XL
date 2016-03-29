package com.Shop.Service;

import com.Shop.Dao.*;
import com.Shop.Model.*;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private RolesDao rolesDao;
    @Autowired
    private OrderProductDao orderProductDao;
    @Autowired
    private WatchProductDao watchProductDao;


    public String addUser(User user){
        User u = userDao.findByName(user.getUsername());
        JsonObject object = new JsonObject();
        if(u != null){
            object.addProperty("status",false);
            object.addProperty("message","用户名已存在!");
        }else {
            userDao.save(user);
            object.addProperty("status",true);
            object.addProperty("message","添加用户成功");
        }
        return object.toString();
    }

    public User findById(int id){
        return userDao.findById(id);
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public boolean addRoles(Roles roles){
        Roles roles1 = rolesDao.findByName(roles.getName());
        if(roles1 ==null){
            rolesDao.save(roles);
            return true;
        }else{
            return false;
        }

    }

    public boolean addArea(Areas areas){
        Areas a = areaDao.findByName(areas.getName());
        if(a == null){
            areaDao.save(areas);
            return true;
        }else{
            return false;
        }

    }

    public void addOrderProduct(OrderProduct orderProduct){
        orderProductDao.save(orderProduct);
    }

    public User loginUser(User user){
        User u = userDao.findByName(user.getUsername());
        if(u == null){
            return null;
        }else if(!u.getPassword().equals(user.getPassword())){
            return null;
        }
        return u;
    }

    public List<OrderProduct> myCart(int id){
        return orderProductDao.findAllByCartId(id);
    }


    public List<WatchProduct> findAllWatchProduct(int id){
        return watchProductDao.findAllByUserId(id);
    }

    public boolean findWatchProductByUIdAndGId(int user_id,int good_id){
        WatchProduct watchProduct = watchProductDao.findByUIdAndPId(user_id,good_id);
        System.out.println("查看商品！");
        if(watchProduct.getGood() == null){
            System.out.println("商品未查看");
            return false;
        }else{
            System.out.println(watchProduct.getGood().getName());
            return true;
        }
    }

    public void addWatchProduct(WatchProduct watchProduct){
        watchProductDao.save(watchProduct);
    }

    public void buyGood(Cart cart,Good good,String imageAddress,int count){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setCart(cart);
        orderProduct.setGood_id(good.getId());
        orderProduct.setCount(count);
        orderProduct.setName(good.getName());
        orderProduct.setImage(imageAddress);
        orderProduct.setPrices(good.getDumpingPrices());
        addOrderProduct(orderProduct);
    }

    public void watchGood(){

    }
}
