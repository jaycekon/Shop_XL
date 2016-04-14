package com.Shop.Util;

import com.Shop.Model.Address;
import com.Shop.Model.OrderProduct;
import com.Shop.Model.Orders;

import javax.persistence.criteria.Order;
import java.util.List;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class OrderPoJo {
    private Orders orders;
    private List<OrderProduct> orderProduct;

    public OrderPoJo(Orders orders,List<OrderProduct> orderProduct){
        this.orders = orders;
        this.orderProduct = orderProduct;
    }


    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
    }

}
