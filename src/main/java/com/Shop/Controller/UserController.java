package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.CartService;
import com.Shop.Service.GoodService;
import com.Shop.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by Administrator on 2016/3/22 0022.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private GoodService goodService;


    @RequestMapping(value ="/register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String registe(User user,HttpSession session){
        String json = userService.addUser(user);
        return json;
    }


    @RequestMapping(value ="addRoles",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addRoles(Roles roles){
        JsonObject object = new JsonObject();
        userService.addRoles(roles);
        object.addProperty("status",true);
        return object.toString();
    }

    @RequestMapping(value = "addAreas",method = RequestMethod.POST)
    @ResponseBody
    public String addAreas(Areas areas){
        JsonObject object = new JsonObject();
        userService.addArea(areas);
        object.addProperty("status",true);
        return object.toString();
    }

    @RequestMapping(value ="loginUser",method = RequestMethod.POST)
    @ResponseBody
    public String login(User user,HttpSession session){
        User u = userService.loginUser(user);
        JsonObject json = new JsonObject();
        if(u ==null){
            json.addProperty("status",false);
            return json.toString();
        }else{
            session.setAttribute("loginUser",u);
            json.addProperty("status",true);
            return json.toString();
        }
    }

    @RequestMapping(value ="buyGood",method = RequestMethod.POST)
    public String buyGood(int good_id,int count, HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        if(user == null){
            return "redirect:login";
        }
        Cart cart =cartService.getCartByUserId(user.getId());
        if(cart == null){
            cart = new Cart();
            cart.setUser(user);
            cartService.addCart(cart);
        }
        Good good = goodService.findGoodById(good_id);
        if(userService.findWatchProductByUIdAndGId(user.getId(),good_id)) {
            List<Image> images = goodService.findImageByGoodId(good_id);
            String imageAddress = images.get(0).getAddress();
            userService.buyGood(cart, good, imageAddress, count);
        }else{
            System.out.println("并未查看商品");
        }
        return "";
    }


    @RequestMapping(value = "watchGood",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String watchGood(HttpSession session,int good_Id){
        JsonObject object = new JsonObject();
        if(session.getAttribute("loginUser") == null){
            object.addProperty("status",false);
            object.addProperty("message","用户未登录");
            return object.toString();
        }
        User user = (User)session.getAttribute("loginUser");
        Good good = goodService.findGoodById(good_Id);
        List<WatchProduct> watchProducts = userService.findAllWatchProduct(user.getId());
        for(WatchProduct watchProduct:watchProducts){
            if(watchProduct.getGood().getId() == good.getId()){
                object.addProperty("status",true);
                object.addProperty("message","用户已查看过该商品");
                return  object.toString();
            }
        }
        WatchProduct watchProduct = new WatchProduct();
        watchProduct.setUser(user);
        watchProduct.setGood(good);
        userService.addWatchProduct(watchProduct);
        object.addProperty("status",true);
        object.addProperty("message","用户已成功查看该商品");
        return object.toString();
    }

    @RequestMapping(value ="myCart",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String myCart(HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        if(user == null){
            object.addProperty("status",false);
            return object.toString();
        }
        Cart cart = cartService.getCartByUserId(user.getId());
        List<OrderProduct> orderProducts = userService.myCart(cart.getId());
        for(OrderProduct orderProduct:orderProducts){
            orderProduct.setCart(null);
        }
        String json = gson.toJson(orderProducts);
        System.out.println(json);
        return json;
    }

    public String payCart(int cart_id,HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        Cart cart = cartService.getByCartId(cart_id);
        List<OrderProduct> orderProducts = userService.myCart(cart.getId());
        Orders orders = new Orders();
        orders.setUser(user);
        double prices = 0;
        for(OrderProduct orderProduct:orderProducts){
            orderProduct.setCart(null);
            orderProduct.setOrders(orders);
        }
        return null;
    }


}
