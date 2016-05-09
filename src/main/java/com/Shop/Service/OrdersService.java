package com.Shop.Service;

import com.Shop.Dao.*;
import com.Shop.Model.*;
import com.Shop.Util.Page;
import org.apache.log4j.Logger;
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
    @Autowired
    GoodDao goodDao;
    @Autowired
    ExitOrdersDao exitOrdersDao;
    Logger log = Logger.getLogger(OrdersService.class);

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

    public List<Orders> findAllByStatusAndUserId(int user_id){
        List<Orders> orderses = ordersDao.findAllByStatusAndUserId(user_id);
        return orderses;
    }

//    public List<Orders> findAllByStatusAndUserId(int user_id){
//        List<Orders> orderses = ordersDao.findAllByStatusAndUserId(user_id);
//        return orderses;
//    }

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

    public List<WithdrawalsOrder> findAllWithdrawalsOrder(){
        return withdrawalsOrderDao.findAll();
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByRole(){
        return withdrawalsOrderDao.findAllByRole();
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByRoleAndPage(Page page){
        return withdrawalsOrderDao.findAllByRoleAndPage(page);
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByAreas(){
        return withdrawalsOrderDao.findAllByArea();
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByAreasAndPage(Page page){
        return withdrawalsOrderDao.findAllByAreaAndPage(page);
    }
    public List<WithdrawalsOrder> findWithdrawalsOrderByAreaId(int area_id){
        return withdrawalsOrderDao.findAllByAreaId(area_id);
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByAreasAndStatus(int status){
        return  withdrawalsOrderDao.findAllByAreaAndStatus(status);
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByAreasAndStatusAndPage(int status,Page page){
        return  withdrawalsOrderDao.findAllByAreaAndStatusAndPage(status,page);
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByRolesAndStatus(int status){
        return  withdrawalsOrderDao.findAllByRoleAndStatus(status);
    }

    public List<WithdrawalsOrder> findAllWithdrawalsOrderByRolesAndStatusAndPage(int status,Page page){
        return  withdrawalsOrderDao.findAllByRoleAndStatusAndPage(status,page);
    }

    public List<WithdrawalsOrder> findWithdrawalsOrderByRoleId(int role_id){
        return withdrawalsOrderDao.findAllByRoleId(role_id);
    }

    public void updateWithdrawalsOrder(WithdrawalsOrder withdrawalsOrder){
        withdrawalsOrderDao.update(withdrawalsOrder);
    }


    public void updateOrderProductAfterPay(int order_id){
        List<OrderProduct> orderProducts = orderProductDao.findAllByOrderId(order_id);
        for(OrderProduct orderProduct :orderProducts){
            Good good = goodDao.findById(orderProduct.getGood_id());
            int num = good.getNum()-orderProduct.getCount();
            good.setNum(num);           //设置商品的库存
            log.info(good.getNum());
            int sale_count = good.getSaleCount() +orderProduct.getCount();
            good.setSaleCount(sale_count);   //设置销量
            log.info(good.getSaleCount());
            goodDao.update(good);
        }
    }

    public void addExitOrders(ExitOrders exitOrders){
        exitOrdersDao.save(exitOrders);
    }

    public void deleteExitOrders(ExitOrders exitOrders){
        exitOrdersDao.delete(exitOrders);
    }

    public void updateExitOrders(ExitOrders exitOrders){
        exitOrdersDao.update(exitOrders);
    }
}
