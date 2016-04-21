package com.Shop.Util;

import com.Shop.Service.OrdersService;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class Test {

    public static void main(String[] args){
        OrdersService ordersService = new OrdersService();
        System.out.println(ordersService.findOrdersByUid("89a9f347-edd6"));
    }
}
