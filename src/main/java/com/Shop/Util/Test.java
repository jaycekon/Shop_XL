package com.Shop.Util;

import com.Shop.Service.OrdersService;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class Test {

    public static void main(String[] args) {
        int i = 0;
        int j = 0;
        for(;i<100;i++){
            i++;
            j= j++;
        }
        System.out.println(i+","+j);
    }
}
