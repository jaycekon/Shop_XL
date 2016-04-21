package com.Shop.Service;

import com.Shop.Dao.OrderProductDao;
import com.Shop.Dao.OrdersDao;
import com.Shop.Dao.WithdrawalsOrderDao;
import com.Shop.Model.OrderProduct;
import com.Shop.Model.Orders;
import com.Shop.Model.WithdrawalsOrder;
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
    @Autowired
    WithdrawalsOrderDao withdrawalsOrderDao;

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

    /**
     * 更新订单项信息
     * @param orderProduct
     */
    public void updateOrderProduct(OrderProduct orderProduct){
        orderProductDao.update(orderProduct);
    }

    /**
     * 通过ID获取订单项
     * @param id
     * @return
     */
    public OrderProduct findOrderProductById(int id){
        return  orderProductDao.findById(id);
    }


    public List<Orders> findOrdersByAreaId(int area_id){
        return ordersDao.findAllByAreaId(area_id);
    }

    public List<Orders> findOrdersByRoleId(int role_id){
        return ordersDao.findAllByRoleId(role_id);
    }

    public Orders findOrdersByUid(String uid){
        return ordersDao.findOrdersByUid(uid);
    }


    public void addWithdrawalsOrder(WithdrawalsOrder withdrawalsOrder){
        withdrawalsOrderDao.save(withdrawalsOrder);
    }

    public WithdrawalsOrder findWithdrawalsOrderById(int id){
        return withdrawalsOrderDao.findById(id);
    }

    public List<WithdrawalsOrder> findWithdrawalsOrderByAreaId(int area_id){
        return withdrawalsOrderDao.findAllByAreaId(area_id);
    }

    public List<WithdrawalsOrder> findWithdrawalsOrderByRoleId(int role_id){
        return withdrawalsOrderDao.findAllByRoleId(role_id);
    }
}
