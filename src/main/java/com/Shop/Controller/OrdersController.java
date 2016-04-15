package com.Shop.Controller;

import com.Shop.Model.Orders;
import com.Shop.Model.User;
import com.Shop.Service.OrdersService;
import com.Shop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Controller
public class OrdersController {
    @Autowired
    UserService userService;
    @Autowired
    OrdersService ordersService;

    /**
     * 获取未付款订单
     * @param model
     * @return
     */
    public String listOrdersByF0(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByFAndUserId(0,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/";
    }

    /**
     * 获取已付款订单
     * @param model
     * @return
     */
    public String listOrdersByF1(Model model){
        List<Orders> orderses = userService.listOrdersByF(1);
        model.addAttribute("orderses",orderses);
        return "frontStage/user/";
    }

    public String listOrdersByC0(Model model){
        List<Orders> orderses = userService.listOrdersByF(0);
        model.addAttribute("orderses",orderses);
        return "frontStage/user/";
    }


}
