package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.*;
import com.Shop.Util.OrderPoJo;
import com.Shop.Util.Page;
import com.Shop.Util.XMLUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


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
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrdersService ordersService;
    Logger log = Logger.getLogger(UserController.class);

    /**
     *商城入口，判断有没有openId，如果没有，请求获得openId，然后从数据库中取出相应的身份
     * @param model
     * @param session
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
//        if(session.getAttribute("openId") == null){
//            log.info("没有openId");
//            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05208e667b03b794&"
//                    + "redirect_uri=http://weijiehuang.productshow.cn/getCode"
//                    +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
//            response.sendRedirect(url);
//        }
//        else{
//            String openId =(String)session.getAttribute("openId");
//            log.info(openId);
//            if(terraceService.findAreasByOpenId(openId)!=null){
//                Areas areas = terraceService.findAreasByOpenId(openId);
//                session.setAttribute("areas",areas);
//                log.info(areas.getImg());
//                return "frontStage/User/AreaCenter";
//            }else if(terraceService.findRolesByOpenId(openId)!=null){
//                Roles roles = terraceService.findRolesByOpenId(openId);
//                session.setAttribute("roles",roles);
//                return "frontStage/User/RoleCenter";
//            }
//            response.sendRedirect("http://weijiehuang.productshow.cn/index");
//        }
//        return null;
        log.info("进入主页！/");
        if(session.getAttribute("loginUser")!=null) {
            List<Good> goods = goodService.listGood();
            model.addAttribute("goods", goods);
            if (session.getAttribute("loginUser") != null) {
                User user = (User) session.getAttribute("loginUser");
                List<WatchProduct> watchProducts = userService.findAllWatchProduct(user.getId());
                model.addAttribute("watchProducts", watchProducts);
                List<Image> images = goodService.findImage();
                model.addAttribute("images",images);
            }
            return "frontStage/index";
        }else if(session.getAttribute("areas")!=null){
            return "frontStage/User/AreaCenter";
        }else if(session.getAttribute("roles")!=null){
            return "frontStage/User/RoleCenter";
        }
        return null;
    }

    /**
     * 商城主页，主要展示所有商品
     * @param model
     * @param session
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        log.info("进入主页！index");
        List<Good> goods = goodService.listGood();
        model.addAttribute("goods", goods);
        if(session.getAttribute("loginUser")!=null){
            User user =(User)session.getAttribute("loginUser");
            List<WatchProduct> watchProducts = userService.findAllWatchProduct(user.getId());
            model.addAttribute("watchProducts",watchProducts);
            List<Image> images = goodService.findImage();
            model.addAttribute("images",images);
        }
        return "frontStage/index";
    }



    @RequestMapping(value = "buyGood", method = RequestMethod.POST)
    public String buyGood(int good_id, int count, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        Good good = goodService.findGoodById(good_id);
        if(count >good.getNum()){
            return "redirect:/Detail/"+good_id;
        }
        if(count <good.getWholesaleCount()){
            return "redirect:/Detail/"+good_id;
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
        if (userService.findWatchProductByUIdAndGId(user.getId(), good_id)!=null) {
            int num = cart.getCount();
            num += count;
            cart.setCount(num);
            double prices = cart.getTotalPrices();

            prices += good.getDumpingPrices() * count;
            cart.setTotalPrices(prices);
            cartService.updateCart(cart);
            List<Image> images = goodService.findImageByGoodId(good_id);
            String imageAddress = null;
            if (images.size() > 0) {
                imageAddress = images.get(0).getAddress();
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
            return "redirect:/myCount/0";
        }
        CountOrder countOrder = new CountOrder();
        countOrder.setUser(user);
        countOrder.setDate(new Date());
        countOrder.setTypes("使用");
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
        List<Address> addresses = addressService.listAddressByUserAndFlag(user.getId());

        List<OrderProduct> orderProducts = userService.findOrderProductByCartId(cart.getId());
        if(addresses.size()>0) {
            model.addAttribute("address", addresses.get(0));
        }
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
        Area city = address.getA().getArea();
        Area s = city.getArea();
        orders.setAddress(s.getName()+city.getName()+address.getA().getName()+address.getAddress());
        orders.setName(address.getUsername());
        orders.setPhone(address.getPhone());
        orders.setUser(user);
        orders.setUuid(UUID.randomUUID().toString());
        orders.setSetTime(new Date());
        int count = 0;
        float prices = 0;
        float totalPV = 0;
        userService.addOrders(orders);
        //通过累加每个订单项的总PV获取到订单的总PV
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setCart(null);
            //累加每个订单项的PV值
            totalPV += orderProduct.getPv() * orderProduct.getCount();
            orderProduct.setOrders(orders);
            userService.updateOrderProduct(orderProduct);
            count += orderProduct.getCount();
            prices += orderProduct.getPrices() * orderProduct.getCount();
        }
        log.info("总的PV值:"+totalPV);
        orders.setNumber(count);
        orders.setPrices(prices);
        //设置货款
        orders.setTotalProfit(prices-totalPV);

        //如果店家上级不为空，则划分佣金，为空佣金归平台
        if (user.getRoles() != null) {
            Roles roles = userService.getRoles(user.getRoles().getId());
            orders.setRoles(roles);
            Areas areas = userService.getAreas(roles.getAreas().getId());
            orders.setAreas(areas);
        }

        //用于计算总的pv，货款通过总价减去pv获得
        orders.setTotalPV(totalPV);
        cartService.deleteCart(cart);
        userService.updateOrders(orders);
        return "redirect:/weixin/preparePayOrder/"+orders.getId();
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
        return "frontStage/User/listAddress";
    }

    @RequestMapping(value = "myAddress", method = RequestMethod.GET)
    public String myAddress(Model model, HttpSession session) {
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
        long time =0;
        long nd = 1000*24*60*60;
        for(Orders orders :orderses){
            if(orders.getD()==0&&orders.getP() == 2) {
                if(orders.getSentTime()!=null) {
                    time = new Date().getTime() - orders.getSentTime().getTime();
                    long day = time / nd;
                    log.info("发货时间" + orders.getSentTime().getTime() + ",现在时间" + new Date().getTime());
                    log.info("发货后天数：" + day);
                    if (day > 14 && orders.getStatus() == 0) {
                        orders.setD(1);
                        ordersService.updateOrders(orders);

                    }
                }
            }
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

    @RequestMapping(value ="/personSign",method = RequestMethod.GET)
    public String memberSign(Model model,HttpSession session){
        User user =(User)session.getAttribute("loginUser");
        if(user.getSign()==1){
            return "redirect:/index";
        }
        Profit profit = terraceService.findProfit();
        model.addAttribute("profit",profit);
        return "frontStage/User/memberCeritification";
    }

    @RequestMapping(value ="/memberSign",method = RequestMethod.GET)
    public String memberSign(HttpSession session){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        User user =(User)session.getAttribute("loginUser");
        if(user.getSign() ==1){
            return "redirect:/";
        }
        Profit profit = terraceService.findProfit();
        CountOrder countOrder = new CountOrder();
        countOrder.setDate(new Date());
        countOrder.setTypes("会员认证");
        countOrder.setCount(profit.getDumpingCount());
        countOrder.setStatus(0);
        countOrder.setPrices(profit.getRecordPrices());
        countOrder.setUuid(UUID.randomUUID().toString());
        countOrder.setUser(user);
        userService.addCountOrder(countOrder);
        return "redirect:/weixin/preparePayCountOrder/"+countOrder.getId();
    }

    @RequestMapping(value ="personSignSuccess",method = RequestMethod.POST)
    public String memberSign(HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("回调url");

        BufferedReader reader = request.getReader();
        String line = "";
        StringBuffer inputString = new StringBuffer();
        try{
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            request.getReader().close();
            System.out.println("----接收到的报文---"+inputString.toString());
            Map<String, Object> map = XMLUtil.parseXML(inputString.toString());

            String resXml="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA]></return_msg></xml>";   //告诉微信服务器，我收到信息了，不要在调用回调action了
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();

            //支付成功，可以再次进行对数据库的操作
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                    log.info("倾销币处理");
                    String uuid= (String)map.get("out_trade_no");
                    CountOrder countOrder = userService.findCountOrderByUUid(uuid);
                if(countOrder.getTypes().equals("会员认证")) {
                    log.info("会员认证处理");
                    User user = userService.findById(countOrder.getUser().getId());
                    user.setSign(1);
                    countOrder.setStatus(1);
                    int count = countOrder.getCount() + user.getCount();
                    user.setCount(count);
                    userService.updateUser(user);
                    userService.updateCountOrder(countOrder);
                }else if(countOrder.getTypes().equals("充值")){
                    log.info("充值处理");
                    User user = userService.findById(countOrder.getUser().getId());
                    countOrder.setStatus(1);
                    int count = countOrder.getCount() + user.getCount();
                    user.setCount(count);
                    userService.updateUser(user);
                    userService.updateCountOrder(countOrder);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value ="myCount/{count}",method = RequestMethod.GET)
    public String myCount(HttpSession session,Model model,@PathVariable("count")int count){
        if(session.getAttribute("loginUser")==null){
            return "redirect:/login";
        }
        User user =(User)session.getAttribute("loginUser");
        User u = userService.findById(user.getId());

        session.setAttribute("loginUser",u);
        Page page = new Page();

        List<CountOrder> countOrders = userService.listCountOrderByUserId(user.getId());
        page.setBeginIndex(count);
        page.setEveryPage(10);
        page.setTotalCount(countOrders.size());
        List<CountOrder> countOrder = userService.listCountOrderByUserId(page,user.getId());
        model.addAttribute("countOrders",countOrder);
        model.addAttribute("page",page);
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
        Profit profit = terraceService.findProfit();
        float prices = profit.getCountPrices()*count;

        User user =(User)session.getAttribute("loginUser");
        CountOrder countOrder =new CountOrder();
        countOrder.setPrices(prices);
        countOrder.setCount(count);
        countOrder.setStatus(0);
        countOrder.setTypes("充值");
        countOrder.setUser(user);
        countOrder.setDate(new Date());
        countOrder.setUuid(UUID.randomUUID().toString());
        userService.addCountOrder(countOrder);
        return "redirect:/weixin/preparePayCountOrder/"+countOrder.getId();
    }

    @RequestMapping(value ="addAddress/{flagt}",method = RequestMethod.GET)
    public String addAddress(HttpSession session,Model model,@PathVariable("flagt")int flagt){
        if(session.getAttribute("loginUser")==null){
            String openId =(String)session.getAttribute("openId");
            User user = terraceService.findUseByOpenId(openId);

            session.setAttribute("loginUser",user);
        }
        List<Area> areas = addressService.findTopArea();
        model.addAttribute("areas",areas);
        model.addAttribute("flag",flagt);
        return "frontStage/User/addAddress";
    }

    @RequestMapping(value ="/findCity",method = RequestMethod.GET)
    @ResponseBody
    public List<Area> findCity(int area_id){
        return addressService.findAllAreaByAreaId(area_id);
    }

    @RequestMapping(value ="addAddress/{flagt}",method = RequestMethod.POST)
    public String addAddress(Address address,HttpSession session,int area_id,@PathVariable("flagt")int flagt){
        User user =(User)session.getAttribute("loginUser");
        Area area= addressService.findAreaById(area_id);
        User u  = userService.findById(user.getId());
        address.setArea(area.getName());
        address.setA(area);
        log.info("添加地址");
        address.setUser(u);
        if (address.getFlag()==1){
            List<Address> addresses = addressService.listAddressByUserAndFlag(u.getId());
            for(Address a:addresses){
                a.setFlag(0);
                log.info("更新地址为普通地址");
                addressService.updateAddress(a);
            }
        }
        userService.addAddress(address);
        if(flagt==0) {
            return "redirect:/listAddress";
        }else{
            return "redirect:/myAddress/"+address.getId();
        }
    }


    @RequestMapping(value ="editAddress/{id}",method = RequestMethod.GET)
    public String editAddress(@PathVariable(value ="id")int id,Model model,int flagt){
        Address address = userService.findAddressById(id);
        List<Area> areas = addressService.findTopArea();
        model.addAttribute("address",address);
        model.addAttribute("areas",areas);
        model.addAttribute("flagt",flagt);
        return "frontStage/User/editAddress";
    }

    @RequestMapping(value ="editAddress",method = RequestMethod.POST)
    public String editAddress(Address address,int area_id,HttpSession session,int flagt){
        log.info(area_id);
        Area area = addressService.findAreaById(area_id);
        Address a = userService.findAddressById(address.getId());
        User user = (User)session.getAttribute("loginUser");
        a.setUsername(address.getUsername());
        a.setAddress(address.getAddress());
        a.setFlag(address.getFlag());
        a.setPhone(address.getPhone());
        a.setArea(area.getName());
        a.setUser(user);
        a.setA(area);
        userService.updateAddress(a);
        if(flagt ==1){
            return "redirect:/myAddress/"+a.getId();
        }
        return "redirect:/listAddress";
    }

    @RequestMapping(value ="roleCenter",method = RequestMethod.GET)
    public String roleCenter(){
        return "frontStage/User/RoleCenter";
    }

    @RequestMapping(value ="areaCenter",method = RequestMethod.GET)
    public String areaCenter(){
        return "frontStage/User/AreaCenter";
    }

    @RequestMapping(value = "roleList",method = RequestMethod.GET)
    public String roleList(HttpSession session,Model model){
        Page<Roles> page = new Page<>();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        Areas areas =(Areas)session.getAttribute("areas");
        List<Roles> roles = userService.listRolesByAreas(areas.getId());
        page.setTotalCount(roles.size());
        roles = userService.listRolesByAreasAndPage(areas.getId(),page);
        page.setList(roles);
        model.addAttribute("page",page);
        return "frontStage/User/roleList";
    }

    @RequestMapping(value = "roleList/{page}",method = RequestMethod.GET)
    public String roleList(HttpSession session,Model model,@PathVariable("page") int pages){
        Page<Roles> page = new Page<>();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        Areas areas =(Areas)session.getAttribute("areas");
        List<Roles> roles = userService.listRolesByAreas(areas.getId());
        page.setTotalCount(roles.size());
        roles = userService.listRolesByAreasAndPage(areas.getId(),page);
        page.setList(roles);
        model.addAttribute("page",page);
        return "frontStage/User/roleList";
    }

    @RequestMapping(value = "userList",method = RequestMethod.GET)
    public String userList(HttpSession session ,Model model){
        Page<User> page = new Page<>();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        Roles roles =(Roles)session.getAttribute("roles");
        List<User> users = userService.listUserByRolesId(roles.getId());
        page.setTotalCount(users.size());
        users = userService.listUserByRolesIdAndPage(roles.getId(),page);
        page.setList(users);
        model.addAttribute("page",page);
        return "frontStage/User/userList";
    }

    @RequestMapping(value = "userList/{page}",method = RequestMethod.GET)
    public String userList(HttpSession session ,Model model,@PathVariable("page")int pages){
        Page<User> page = new Page<>();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        Roles roles =(Roles)session.getAttribute("roles");
        List<User> users = userService.listUserByRolesId(roles.getId());
        page.setTotalCount(users.size());
        users = userService.listUserByRolesIdAndPage(roles.getId(),page);
        page.setList(users);
        model.addAttribute("page",page);
        return "frontStage/User/userList";
    }


    @RequestMapping(value ="/withdrawProfit",method =RequestMethod.GET)
    public String areaProfit(){
        return "frontStage/User/Withdraw";
    }

    @RequestMapping(value="/deleteAddress/{id}",method =RequestMethod.GET)
    public String deleteAddress(@PathVariable("id")int id){
        addressService.deleteAddress(id);
        return "redirect:/listAddress";
    }






}
