package com.Shop.Service;

import com.Shop.Dao.*;
import com.Shop.Model.*;
import com.Shop.Util.Page;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AreasDao areasDao;
    @Autowired
    private RolesDao rolesDao;
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private OrderProductDao orderProductDao;
    @Autowired
    private WatchProductDao watchProductDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ProfitDao profitDao;
    @Autowired
    private CountOrderDao countOrderDao;


    public String addUser(User user){
        User u = userDao.findByOpenId(user.getOpenId());
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

    public void updateUser(User user){
        userDao.update(user);
    }
    public User findById(int id){
        return userDao.findById(id);
    }

    public List<User> listUser(){
        return userDao.findAll();
    }

    public List<User> listUserByRolesId(int roles_id){
        return userDao.findAllByRolesId(roles_id);
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
    public void updateRoles(Roles roles){
        rolesDao.update(roles);
    }

    public Roles getRoles(int id){
        return rolesDao.findById(id);
    }

    public List<Roles> listRoles(){
        return rolesDao.findAll();
    }

    public List<Roles> listRolesByAreas(int areas_id){
        return rolesDao.findAllByAreasId(areas_id);
    }

    public boolean addArea(Areas areas){
        Areas a = areasDao.findByOpenId(areas.getOpenId());
        if(a == null){
            areasDao.save(areas);
            return true;
        }else{
            return false;
        }

    }

    public Areas getAreas(int id){
        return areasDao.findById(id);
    }

    public void updateAreas(Areas areas){
        areasDao.update(areas);
    }
    public List<Areas> listAreas(){
        return areasDao.findAll();
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

//    public List<OrderProduct> myCart(int id){
//        return orderProductDao.findAllByCartId(id);
//    }


    public List<WatchProduct> findAllWatchProduct(int id){
        return watchProductDao.findAllByUserId(id);
    }

    public WatchProduct findWatchProductByUIdAndGId(int user_id,int good_id){
        WatchProduct watchProduct = watchProductDao.findByUIdAndPId(user_id,good_id);
        if(watchProduct == null){
            return null;
        }else{
            return watchProduct;
        }
    }

    public void addWatchProduct(WatchProduct watchProduct){
        watchProductDao.save(watchProduct);
    }

    public OrderProduct addOrderProduct(Cart cart,Good good,String imageAddress,int count){
        OrderProduct orderProduct = new OrderProduct();
        Profit profit = profitDao.findById();
        List<OrderProduct> orderProducts = orderProductDao.findAllByCartId(cart.getId());
        for(OrderProduct orderProduct1:orderProducts){
            if(orderProduct1.getGood_id()==good.getId()){
                int c = orderProduct1.getCount()+count;
                orderProduct1.setCount(c);
                float areaProfit = good.getPv() * count * profit.getArea_count();
                orderProduct1.setAreaProfit(areaProfit/100);
                float roleProfit = good.getPv() * count * profit.getRole_count();
                orderProduct1.setRoleProfit(roleProfit/100);
                orderProductDao.update(orderProduct1);
                return orderProduct1;
            }
        }
        orderProduct.setCart(cart);
        orderProduct.setGood_id(good.getId());
        orderProduct.setCount(count);
        orderProduct.setName(good.getName());
        orderProduct.setImage(imageAddress);
        orderProduct.setPrices(good.getDumpingPrices());
        orderProduct.setDescribes(good.getDescribes());
        float areaProfit = good.getPv() * count * profit.getArea_count();
        orderProduct.setAreaProfit(areaProfit/100);
        float roleProfit = good.getPv() * count * profit.getRole_count();
        orderProduct.setRoleProfit(roleProfit/100);
        orderProductDao.save(orderProduct);
        return orderProduct;
    }


    public HashMap addOrderProduct(int id,int count,User user){
        //创建订单
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setNumber(count);
        orders.setSetTime(new Date());
        ordersDao.save(orders);

        //创建订单项
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setCount(count);
        Good good = goodDao.findById(id);
        orderProduct.setName(good.getName());
        orderProduct.setPrices(good.getDumpingPrices());
        orderProduct.setGood_id(good.getId());
        List<Image> images = imageDao.findAllByGoodId(good.getId());
        orderProduct.setImage(images.get(0).getAddress());
        orderProduct.setOrders(orders);
        orderProductDao.save(orderProduct);
        List<OrderProduct> orderProucts = new ArrayList<>();
        orderProucts.add(orderProduct);
        HashMap map = new HashMap();
        map.put("orders",orders);
        map.put("orderProduct",orderProucts);
        return map;
    }

    public void addAddress(Address address){
        addressDao.save(address);
    }
    public Address findAddressById(int id){
        return addressDao.findById(id);
    }

    public  void updateAddress(Address address){
        addressDao.update(address);
    }
    public List<Address> listAddress(int id){
        return addressDao.findByUserId(id);
    }


    public void updateOrderProduct(OrderProduct orderProduct){
        orderProductDao.update(orderProduct);
    }
    public void addOrders(Orders orders){
        ordersDao.save(orders);
    }

    public Orders findOrdersById(int id){
        return ordersDao.findById(id);
    }
    public void updateOrders(Orders orders){
        ordersDao.update(orders);
    }

    public List<Orders> listOrders(){
        return ordersDao.findAll();
    }
    public List<Orders> listOrdersByUser(int user_id){
        return ordersDao.findAllByUser(user_id);
    }

    public List<Orders> listOrdersByF(int f){
        return ordersDao.findAllByF(f);
    }
    public List<Orders> listOrdersByP(int p){
        return ordersDao.findAllByP(p);
    }
    public List<Orders> listOrdersByC(int c){
        return ordersDao.findAllByC(c);
    }
    public List<Orders> listOrdersByT(int t){
        return ordersDao.findAllByT(t);
    }
    public List<Orders> listOrdersByD(int d){
        return ordersDao.findAllByD(d);
    }

    public List<OrderProduct> findOrderProductByOrderId(int order_id){
        return orderProductDao.findAllByOrderId(order_id);
    }

    public List<OrderProduct> findOrderProductByCartId(int cart_id){
        return orderProductDao.findAllByCartId(cart_id);
    }

    public OrderProduct findOrderProductById(int id){
        return orderProductDao.findById(id);
    }

    public void deleteOrderProduct(OrderProduct orderProduct){
        orderProductDao.delete(orderProduct);
    }


    public void addCountOrder(CountOrder countOrder){
        countOrderDao.save(countOrder);
    }

    public List<CountOrder> listCountOrderByUserId(int user_id){
        return countOrderDao.findAllByUserId(user_id);
    }

    public List<CountOrder> listCountOrderByUserId(Page page,int user_id){
        return countOrderDao.findAllByUserId(page,user_id);
    }
}
