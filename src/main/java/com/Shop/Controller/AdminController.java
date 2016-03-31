package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.Shop.Util.OrderPoJo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Controller
public class AdminController {
    @Autowired
    private TerraceService terraceService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        return "backStage/index";
    }

    @RequestMapping(value = "loginTerrace",method = RequestMethod.GET)
    public String login(){
        return "backStage/login";
    }

    @RequestMapping(value = "loginTerrace",method = RequestMethod.POST)
    public String login(Terrace terrace, HttpSession session){
        Terrace t = terraceService.loginTerrace(terrace);
        if(t == null){
            return "redirect:/loginTerrace";
        }else{
            session.setAttribute("loginTerrace",t);
           return "redirect:/listGood";
        }
    }

    @RequestMapping(value = "logoutTerrace",method = RequestMethod.GET)
    public String logOut(HttpSession session){
        session.setAttribute("loginTerrace",null);
        return "redirect:/loginTerrace";
    }

    @RequestMapping(value ="updateTerrace",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(Terrace terrace,HttpSession session){
        JsonObject object = new JsonObject();
        if(session.getAttribute("loginTerrace") == null){
            object.addProperty("status",false);
            object.addProperty("message","管理员未登录");
        }else{
            terraceService.updateTerrace(terrace);
            object.addProperty("status",true);
        }
        return object.toString();
    }

    @RequestMapping(value ="addProfit",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String setProfit(Profit profit){
        JsonObject object = new JsonObject();
        terraceService.addProfit(profit);
        object.addProperty("status",true);
        return object.toString();
    }

    @RequestMapping(value ="listOrder",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String listOrder(HttpSession session, Model model){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        List<Orders> orders = userService.listOrders();
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value ="listOrderByF",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listOrderByF(HttpSession session, Model model,int f){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        List<Orders> orders = userService.listOrdersByF(f);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        Gson gson = new Gson();
        return gson.toJson(orderPoJos);
    }

    @RequestMapping(value ="listOrderByP",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listOrderByP(HttpSession session, Model model,int p){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        List<Orders> orders = userService.listOrdersByP(p);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        Gson gson = new Gson();
        return gson.toJson(orderPoJos);
    }

    @RequestMapping(value ="listOrderByD",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listOrderByD(HttpSession session, Model model,int d){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        List<Orders> orders = userService.listOrdersByD(d);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        Gson gson = new Gson();
        return gson.toJson(orderPoJos);
    }

    @RequestMapping(value ="listOrderByT",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listOrderByT(HttpSession session, Model model,int t){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        List<Orders> orders = userService.listOrdersByT(t);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        Gson gson = new Gson();
        return gson.toJson(orderPoJos);
    }

    @RequestMapping(value ="listOrderByC",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listOrderByC(HttpSession session, Model model,int c){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        List<Orders> orders = userService.listOrdersByC(c);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        Gson gson = new Gson();
        return gson.toJson(orderPoJos);
    }

    @RequestMapping(value ="findOrderById",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findOrder(int id,HttpSession session,Model model){
        if(session.getAttribute("loginTerrace") == null){
            return "redirect:/loginTerrace";
        }
        Orders orders = userService.findOrdersById(id);
        List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
        OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
        model.addAttribute("orderPojo",orderPoJo);
        Gson gson = new Gson();
        return gson.toJson(orderPoJo);
    }


    @RequestMapping(value ="listAreas",method = RequestMethod.GET)
    public String listAreas(Model model){
        List<Areas> areas = userService.listAreas();
        model.addAttribute("areas",areas);
        return "backStage/User/listArea";
    }

    @RequestMapping(value ="listRoles",method = RequestMethod.GET)
    public String listRoles(Model model){
        List<Roles> roles = userService.listRoles();
        model.addAttribute("roles",roles);
        return "backStage/User/listRoles";
    }

    @RequestMapping(value ="listUser",method = RequestMethod.GET)
    public String listUser(Model model){
        List<User> users = userService.listUser();
        model.addAttribute("users",users);
        return "backStage/User/listUser";
    }

}
