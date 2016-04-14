package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.CartService;
import com.Shop.Service.GoodService;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.Shop.Util.AccessTokenUtil;
import com.Shop.Util.OrderPoJo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private TerraceService terraceService;
    Logger log = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if(session.getAttribute("openId") == null){
            log.info("没有openId");
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05208e667b03b794&"
                    + "redirect_uri=http://weijiehuang.productshow.cn/getCode"
                    +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
            response.sendRedirect(url);
        }
        else{
            String openId =(String)session.getAttribute("openId");
            log.info(openId);
            if(terraceService.findAreasByOpenId(openId)!=null){
                Areas areas = terraceService.findAreasByOpenId(openId);
                session.setAttribute("areas",areas);
                log.info(areas.getImg());
                return "frontStage/User/AreaCenter";
            }else if(terraceService.findRolesByOpenId(openId)!=null){
                Roles roles = terraceService.findRolesByOpenId(openId);
                session.setAttribute("roles",roles);
                return "frontStage/User/RoleCenter";
            }
            response.sendRedirect("http://weijiehuang.productshow.cn/index");
        }
        return null;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String inde(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        List<Good> goods = goodService.listGood();
        model.addAttribute("goods", goods);
        if(session.getAttribute("loginUser")!=null){
            User user =(User)session.getAttribute("loginUser");
            List<WatchProduct> watchProducts = userService.findAllWatchProduct(user.getId());
            model.addAttribute("watchProducts",watchProducts);
        }
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
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:login";
        }
        if(user.getSign()==0){
            return "redirect:/personSign";
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
        if (userService.findWatchProductByUIdAndGId(user.getId(), good_id)) {
            List<String> images = goodService.findImageByGoodId(good_id);
            String imageAddress = null;
            if (images.size() > 0) {
                imageAddress = images.get(0);
            }
           userService.addOrderProduct(cart, good, imageAddress, count);
        } else {
            return "redirect:/watchGood/"+good_id;
        }
        return "redirect:/myCart";
    }


    @RequestMapping(value = "watchGood/{id}", method = RequestMethod.GET)
    public String watchGood(HttpSession session, @PathVariable(value="id") int good_Id) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loginUser");
        Good good = goodService.findGoodById(good_Id);
        List<WatchProduct> watchProducts = userService.findAllWatchProduct(user.getId());
        for (WatchProduct watchProduct : watchProducts) {
            if (watchProduct.getGood().getId() == good.getId()) {
                return "redirect:/Detail/"+good_Id;
            }
        }
        if(user.getCount()<good.getwPrices()){
            return "redirect:/myCount";
        }
        CountOrder countOrder = new CountOrder();
        countOrder.setUser(user);
        countOrder.setDate(new Date());
        countOrder.setType("使用");
        countOrder.setStatus(1);
        countOrder.setCount(good.getwPrices());
        int count = user.getCount() - good.getwPrices();
        int usecount = user.getUsecount() +good.getwPrices();
        user.setCount(count);
        user.setUsecount(usecount);
        userService.updateUser(user);
        session.setAttribute("loginUser",user);
        userService.addCountOrder(countOrder);
        WatchProduct watchProduct = new WatchProduct();
        watchProduct.setUser(user);
        watchProduct.setGood(good);
        userService.addWatchProduct(watchProduct);
        return "redirect:/Detail/"+good_Id;
    }

    @RequestMapping(value = "myCart", method = RequestMethod.GET)
    public String myCart(HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loginUser");
        Cart cart = cartService.getCartByUserId(user.getId());
//        if(cart == null){
//            return "redirect:/";
//        }
        if(cart !=null) {
            List<OrderProduct> orderProducts = userService.findOrderProductByCartId(cart.getId());
            model.addAttribute("orderProducts", orderProducts);
        }
        model.addAttribute("cart", cart);
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
        return "redirect:/userOrders";
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
        if(cart.getCount()==0){
            cartService.deleteCart(cart);
        }else {
            cartService.updateCart(cart);
        }
        userService.deleteOrderProduct(orderProduct);
        return "redirect:/myCart";
    }

    @RequestMapping(value ="userOrders",method = RequestMethod.GET)
    public String listOrders(Model model,HttpSession session){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        User user = (User)session.getAttribute("loginUser");
        List<Orders> orderses = userService.listOrdersByUser(user.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }

    @RequestMapping(value ="userOrderDetail/{id}",method = RequestMethod.GET)
    public String orderDetail(@PathVariable(value ="id")int id,Model model,HttpSession session){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        Orders orders = userService.findOrdersById(id);
        List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
        OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
        model.addAttribute("orderPoJo",orderPoJo);
        return "frontStage/User/orderDetail";
    }

    @RequestMapping(value ="personCenter",method = RequestMethod.GET)
    public String personCenter(HttpSession session){
        if(session.getAttribute("loginUser") == null){
            log.info("找不到用户！");
            String openId =(String)session.getAttribute("openId");
            User user = terraceService.findUseByOpenId(openId);
            session.setAttribute("loginUser",user);
        }
        return "frontStage/User/storeCenter";
    }
    @RequestMapping(value ="areasCenter",method = RequestMethod.GET)
    public String areaCenter(HttpSession session){
        return "frontStage/User/AreaCenter";
    }
    @RequestMapping(value ="rolesCenter",method = RequestMethod.GET)
    public String rolesCenter(HttpSession session){
        if(session.getAttribute("loginUser") == null){
            return "redirect:/login";
        }
        return "frontStage/User/storeCenter";
    }


    @RequestMapping(value ="personSign",method = RequestMethod.GET)
    public String memberSign(Model model,HttpSession session){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        User user =(User)session.getAttribute("loginUser");
        if(user.getSign() ==1){
            return "redirect:/";
        }
        Profit profit = terraceService.findProfit();
        model.addAttribute("profit",profit);
        return "frontStage/User/memberCeritification";
    }

    @RequestMapping(value ="personSignSuccess",method = RequestMethod.GET)
    public String memberSign(HttpSession session){
        User user =(User)session.getAttribute("loginUser");
        Profit profit = terraceService.findProfit();
        int count = user.getCount()+profit.getDumpingCount();
        CountOrder countOrder = new CountOrder();
        countOrder.setDate(new Date());
        countOrder.setType("会员认证");
        countOrder.setCount(profit.getDumpingCount());
        countOrder.setStatus(1);
        countOrder.setUser(user);
        user.setCount(count);
        user.setSign(1);
        userService.addCountOrder(countOrder);
        userService.updateUser(user);
        return "redirect:/personCenter";
    }

    @RequestMapping(value ="myCount",method = RequestMethod.GET)
    public String myCount(HttpSession session,Model model){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        User user =(User)session.getAttribute("loginUser");
        List<CountOrder> countOrders = userService.listCountOrderByUserId(user.getId());
        model.addAttribute("countOrders",countOrders);
        return "frontStage/User/myCount";
    }


    @RequestMapping(value ="recharge",method = RequestMethod.GET)
    public String recharge(Model model){
        Profit profit = terraceService.findProfit();
        model.addAttribute(profit);
        return "frontStage/User/recharge";
    }

    @RequestMapping(value ="recharge",method = RequestMethod.POST)
    public String recharge(HttpSession session,int count){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        User user =(User)session.getAttribute("loginUser");
        CountOrder countOrder =new CountOrder();
        countOrder.setCount(count);
        countOrder.setStatus(1);
        countOrder.setType("充值");
        countOrder.setUser(user);
        countOrder.setDate(new Date());
        int num = user.getCount()+count;
        user.setCount(num);
        userService.updateUser(user);
        userService.addCountOrder(countOrder);

        return "redirect:/myCount";

    }

    @RequestMapping(value ="addAddress",method = RequestMethod.GET)
    public String addAddress(HttpSession session){
        if(session.getAttribute("loginUser")==null){
            String openId =(String)session.getAttribute("openId");
            User user = terraceService.findUseByOpenId(openId);
            session.setAttribute("loginUser",user);
        }
        return "frontStage/User/addAddress";
    }

    @RequestMapping(value ="addAddress",method = RequestMethod.POST)
    public String addAddress(Address address,HttpSession session){
        User user =(User)session.getAttribute("loginUser");
        address.setUser(user);
        userService.addAddress(address);
        return "redirect:/listAddress";
    }


    @RequestMapping(value ="editAddress/{id}",method = RequestMethod.GET)
    public String editAddress(@PathVariable(value ="id")int id,Model model){
        Address address = userService.findAddressById(id);
        model.addAttribute("address",address);
        return "frontStage/User/editAddress";
    }

    @RequestMapping(value ="editAddress",method = RequestMethod.POST)
    public String editAddress(Address address){
        Address a = userService.findAddressById(address.getId());
        a.setUsername(address.getUsername());
        a.setAddress(address.getAddress());
        a.setFlag(address.getFlag());
        a.setPhone(address.getPhone());
        a.setArea(address.getArea());
        userService.updateAddress(address);
        return "frontStage/User/addAddress";
    }
}
