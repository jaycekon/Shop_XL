package com.Shop.Service;

import com.Shop.Dao.OrderProductDao;
import com.Shop.Dao.OrdersDao;
import com.Shop.Model.OrderProduct;
import com.Shop.Model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class OrdersService {
    @Autowired
    OrdersDao ordersDao;
    @Autowired
    OrderProductDao orderProductDao;

    public Orders findOrdersById(int id){
        return ordersDao.findById(id);
    }

    public void updateOrders(Orders orders){
        ordersDao.update(orders);
    }


    /**
     * 通过订单支付状态获取订单
     * @param f
     * @param user_id
     * @return
     */
    public List<Orders> findOrdersByFAndUserId(int f,int user_id){
        List<Orders> orderses = ordersDao.findAllByFAndUserId(f,user_id);
        return orderses;
    }

    /**
     * 通过订单状态获取订单
     * @param d
     * @param user_id
     * @return
     */
    public List<Orders> findOrdersByDAndUserId(int d,int user_id){
        List<Orders> orderses = ordersDao.findAllByDAndUserId(d,user_id);
        return orderses;
    }

    /**
     * 通过订单配送状态获取订单
     * @param p
     * @param user_id
     * @return
     */
    public List<Orders> findOrdersByPAndUserId(int p,int user_id){
        List<Orders> orderses = ordersDao.findAllByPAndUserId(p,user_id);
        return orderses;
    }

    /**
     * 通过订单评论状态获取订单
     * @param c
     * @param user_id
     * @return
     */
    public List<Orders> findOrdersByCAndUserId(int c,int user_id){
        List<Orders> orderses = ordersDao.findAllByCAndUserId(c,user_id);
        return orderses;
    }

    /**
     * 通过订单退货状态获取订单
     * @param t
     * @param user_id
     * @return
     */
    public List<Orders> findOrdersByTAndUserId(int t,int user_id){
        List<Orders> orderses = ordersDao.findAllByTAndUserId(t,user_id);
        return orderses;
    }

    public void updateOrderProduct(OrderProduct orderProduct){
        orderProductDao.update(orderProduct);
    }

    public OrderProduct findOrderProductById(int id){
        return  orderProductDao.findById(id);
    }


}
