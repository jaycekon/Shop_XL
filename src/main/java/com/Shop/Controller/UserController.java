package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.CartService;
import com.Shop.Service.GoodService;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.Shop.Util.OrderPoJo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Good> goods = goodService.listGood();
        model.addAttribute("goods", goods);
        return "frontStage/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "frontStage/User/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String registe(User user, HttpSession session) {
        String json = userService.addUser(user);
        return json;
    }


    @RequestMapping(value = "addRoles", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addRoles(Roles roles) {
        JsonObject object = new JsonObject();
        userService.addRoles(roles);
        object.addProperty("status", true);
        return object.toString();
    }

    @RequestMapping(value = "addAreas", method = RequestMethod.POST)
    @ResponseBody
    public String addAreas(Areas areas) {
        JsonObject object = new JsonObject();
        userService.addArea(areas);
        object.addProperty("status", true);
        return object.toString();
    }

    @RequestMapping(value = "loginUser", method = RequestMethod.POST)
    public String login(User user, HttpSession session) {
        User u = userService.loginUser(user);
        session.setAttribute("loginUser", u);
        return "redirect:/";
    }

    @RequestMapping(value = "buyGood", method = RequestMethod.POST)
    public String buyGood(int good_id, int count, HttpSession session) {
        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:login";
        }
        Cart cart = cartService.getCartByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartService.addCart(cart);
        }
        int num = cart.getCount();
        num += count;
        cart.setCount(num);
        double prices = cart.getTotalPrices();
        Good good = goodService.findGoodById(good_id);
        prices += good.getDumpingPrices() * count;
        cart.setTotalPrices(prices);
        cartService.updateCart(cart);
        OrderProduct orderProduct = null;
        if (userService.findWatchProductByUIdAndGId(user.getId(), good_id)) {
            List<String> images = goodService.findImageByGoodId(good_id);
            String imageAddress = null;
            if (images.size() > 0) {
                imageAddress = images.get(0);
            }
            orderProduct = userService.addOrderProduct(cart, good, imageAddress, count);
        } else {
            object.addProperty("status", "并未查看商品");
            return object.toString();
        }
        return "redirect:/myCart";
    }


    @RequestMapping(value = "watchGood", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String watchGood(HttpSession session, int good_Id) {
        JsonObject object = new JsonObject();
        if (session.getAttribute("loginUser") == null) {
            object.addProperty("status", false);
            object.addProperty("message", "用户未登录");
            return object.toString();
        }
        User user = (User) session.getAttribute("loginUser");
        Good good = goodService.findGoodById(good_Id);
        List<WatchProduct> watchProducts = userService.findAllWatchProduct(user.getId());
        for (WatchProduct watchProduct : watchProducts) {
            if (watchProduct.getGood().getId() == good.getId()) {
                object.addProperty("status", true);
                object.addProperty("message", "用户已查看过该商品");
                return object.toString();
            }
        }
        WatchProduct watchProduct = new WatchProduct();
        watchProduct.setUser(user);
        watchProduct.setGood(good);
        userService.addWatchProduct(watchProduct);
        object.addProperty("status", true);
        object.addProperty("message", "用户已成功查看该商品");
        return object.toString();
    }

    @RequestMapping(value = "myCart", method = RequestMethod.GET)
    public String myCart(HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loginUser");
        Cart cart = cartService.getCartByUserId(user.getId());
        if(cart == null){
            return "redirect:/";
        }
        List<OrderProduct> orderProducts = userService.findOrderProductByCartId(cart.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("orderProducts", orderProducts);
        return "frontStage/User/myCart";
    }

    @RequestMapping(value = "createOrder", method = RequestMethod.GET)
    public String payCart(Model model, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loginUser");
        Cart cart = cartService.getCartByUserId(user.getId());
        if (cart == null) {
            return "redirect:/";
        }
        List<Address> addresses = userService.listAddress(user.getId());
        model.addAttribute("addresses", addresses);
        List<OrderProduct> orderProducts = userService.findOrderProductByCartId(cart.getId());
        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("cart", cart);
        return "frontStage/User/createOrder";
    }

    @RequestMapping(value = "createOrder/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String payCart(HttpSession session, @PathVariable(value = "id") int id) {
        User user = (User) session.getAttribute("loginUser");
        Cart cart = cartService.getCartByUserId(user.getId());
        if (cart == null) {
            return null;
        }
        List<OrderProduct> orderProducts = userService.findOrderProductByCartId(cart.getId());
        Address address = userService.findAddressById(id);
        Orders orders = new Orders();
        orders.setAddress(address.getAddress());
        orders.setName(address.getUsername());
        orders.setPhone(address.getPhone());
        orders.setUser(user);
        orders.setSetTime(new Date());
        int count = 0;
        double prices = 0;
        float areaProfit = 0;
        float roleProfit = 0;
        userService.addOrders(orders);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setCart(null);
            orderProduct.setOrders(orders);
            userService.updateOrderProduct(orderProduct);
            count += orderProduct.getCount();
            prices += orderProduct.getPrices() * orderProduct.getCount();
            areaProfit += orderProduct.getAreaProfit();
            roleProfit += orderProduct.getRoleProfit();
        }
        orders.setNumber(count);
        orders.setPrices(prices);
        orders.setAreaProfit(areaProfit);
        orders.setRolesProfit(roleProfit);
        if (user.getRoles() != null) {
            Roles roles = userService.getRoles(user.getRoles().getId());
            orders.setRoles(roles);
            Areas areas = userService.getArea(roles.getAreas().getId());
            orders.setAreas(areas);
        }
        cartService.deleteCart(cart);
        userService.updateOrders(orders);
        Gson gson = new Gson();
        return gson.toJson(orders);
    }

    @RequestMapping(value = "easyBuy", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public OrderPoJo easyBuy(int id, int count, HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return null;
        }
        User user = (User) session.getAttribute("loginUser");
        HashMap map = userService.addOrderProduct(id, count, user);
        model.addAttribute("orders", map.get("orders"));
        model.addAttribute("orderProduct", map.get("orderProduct"));
        List<Address> addresses = userService.listAddress(user.getId());
        model.addAttribute("addresses", addresses);
        OrderPoJo orderPoJo = new OrderPoJo((Orders) map.get("orders"), (List<OrderProduct>) map.get("orderProduct"));
        return orderPoJo;
    }

    @RequestMapping(value = "addAddress", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Address addAddress(HttpSession session, Address address) {
        User user = (User) session.getAttribute("loginUser");
        address.setUser(user);
        userService.addAddress(address);
        return address;
    }

    @RequestMapping(value = "listAddress", method = RequestMethod.GET)
    public String listAddress(Model model, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loginUser");
        List<Address> addresses = userService.listAddress(user.getId());
        model.addAttribute("addresses", addresses);
        return "frontStage/User/myAddress";
    }

    @RequestMapping(value = "myAddress/{id}", method = RequestMethod.GET)
    public String myAddress(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loginUser");
        Cart cart = cartService.getCartByUserId(user.getId());
        if (cart == null) {
            return "redirect:/";
        }

        Address address = userService.findAddressById(id);
        model.addAttribute("address", address);
        List<OrderProduct> orderProducts = userService.findOrderProductByCartId(cart.getId());
        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("cart", cart);
        return "frontStage/User/createOrder";
    }

    @RequestMapping(value = "removeProduct/{id}", method = RequestMethod.GET)
    public String removeProduct(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }
        Cart cart = cartService.getCartByUserId(user.getId());
        int num = cart.getCount();
        OrderProduct orderProduct = userService.findOrderProductById(id);
        num =num - orderProduct.getCount();
        cart.setCount(num);
        double prices = cart.getTotalPrices();
        prices =prices - orderProduct.getPrices()* orderProduct.getCount();
        cart.setTotalPrices(prices);
        cartService.updateCart(cart);
        userService.deleteOrderProduct(orderProduct);
        return "redirect:/myCart";
    }


}
