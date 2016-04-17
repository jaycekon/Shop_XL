package com.Shop.Controller;

import com.Shop.Model.OrderProduct;
import com.Shop.Model.Orders;
import com.Shop.Model.User;
import com.Shop.Service.OrdersService;
import com.Shop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * 获取未付款订单(后台）
     * @param model
     * @return
     */
    @RequestMapping(value ="listOrdersByF0",method = RequestMethod.GET)
    public String listOrdersByF0(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByFAndUserId(0,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取已付款订单，待发货（后台）
     * @param model
     * @return
     */
    @RequestMapping(value ="listOrdersByF1",method = RequestMethod.GET)
    public String listOrdersByF1(Model model){
        List<Orders> orderses = userService.listOrdersByF(1);
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取待评价的订单（后台）
     * @param model
     * @return
     */
    @RequestMapping(value ="listOrdersByC0",method = RequestMethod.GET)
    public String listOrdersByC0(Model model){
        List<Orders> orderses = userService.listOrdersByF(0);
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 订单项申请退货
     * @param id
     * @return
     */
    @RequestMapping(value = "/exitProduct/{id}",method = RequestMethod.GET)
    public String exitOrderProduct(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        if(orderProduct !=null){
            orderProduct.setStauts(1);
            ordersService.updateOrderProduct(orderProduct);
        }
        return "redirect:/listOrders";
    }

    /**
     * 申请退款（订单尚未发货）
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/exitOrder/{id}",method = RequestMethod.GET)
    public String exitOrder(@PathVariable("id") int id,HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        Orders orders =  ordersService.findOrdersById(id);
        if(orders !=null && orders.getP()==0){
            orders.setT(1);
            ordersService.updateOrders(orders);
        }
        List<Orders> orderses = ordersService.findOrdersByTAndUserId(1,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取已完成订单(前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/endOrders",method = RequestMethod.GET)
    public String endOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByDAndUserId(1,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取未付款订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/repayOrders",method = RequestMethod.GET)
    public String repayOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByFAndUserId(0,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取未发货订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/resendOrders",method = RequestMethod.GET)
    public String resendOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByPAndUserId(0,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取已发货订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendOrders",method = RequestMethod.GET)
    public String sendOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByPAndUserId(1,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取待评价订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/reCommentOrders",method = RequestMethod.GET)
    public String reCommentOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByCAndUserId(0,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }

    /**
     * 获取申请退款订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/exitOrders",method = RequestMethod.GET)
    public String exitOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByTAndUserId(1,user.getId());
        model.addAttribute("orderses",orderses);
        return "frontStage/user/orderList";
    }




}
