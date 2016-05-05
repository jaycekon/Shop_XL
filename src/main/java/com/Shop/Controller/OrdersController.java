package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.*;
import com.Shop.Util.MD5;
import com.Shop.Util.OrderPoJo;
import com.Shop.Util.WebChatUtil;
import com.Shop.Util.XMLUtil;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/15.
 */
@Controller
public class OrdersController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CartService cartService;
    @Autowired
    private TerraceService terraceService;
    @Autowired
    private AddressService addressService;
    Logger log = Logger.getLogger(OrdersController.class);
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
        return "frontStage/User/orderList";
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
        return "frontStage/User/orderList";
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
        return "frontStage/User/orderList";
    }


    /**
     * 订单项申请退款
     * @param id
     * @return
     */
    @RequestMapping(value = "/exitProduct/{id}",method = RequestMethod.GET)
    public String exitOrderProduct(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        Orders orders = orderProduct.getOrders();
        if(orderProduct !=null){
            if(orderProduct.getStauts()==1){
                orders.setStatus(0);
                orderProduct.setStauts(0);
            }else if(orderProduct.getStauts()==0){
                orders.setStatus(1);
                orderProduct.setStauts(1);
            }
            ordersService.updateOrders(orders);
            ordersService.updateOrderProduct(orderProduct);
        }
        return "redirect:/exitOrders";
    }

    /**
     * 拒绝退款
     * @param id
     * @return
     */
    @RequestMapping(value = "/disagreeExitOrderProduct/{id}",method = RequestMethod.GET)
    public String disagreeExitOrderProduct(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        Orders orders = orderProduct.getOrders();
        orderProduct.setStauts(3);
        orders.setStatus(0);
        ordersService.updateOrderProduct(orderProduct);
        ordersService.updateOrders(orders);
        return "redirect:/listOrder";
    }


    /**
     * 订单项申请退货
     * @param id
     * @return
     */
    @RequestMapping(value = "/exitGood/{id}",method = RequestMethod.GET)
    public String exitOrderProducts(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        Orders orders = orderProduct.getOrders();
        ExitOrders exitOrders = new ExitOrders();
        exitOrders.setPrices(orderProduct.getPrices()*orderProduct.getCount());
        exitOrders.setSetTime(new Date());
        ordersService.addExitOrders(exitOrders);
        orderProduct.setExitOrders(exitOrders);
        orderProduct.setExitStatus(1);
        ordersService.updateOrderProduct(orderProduct);
        orders.setT(1);
        ordersService.updateOrders(orders);
        return "redirect:/exitGoods";
    }

    /**
     * 平台同意退货
     * @param id
     * @return
     */
    @RequestMapping(value = "/agreeExit/{id}",method = RequestMethod.GET)
    public String agreeExit(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        orderProduct.setExitStatus(2);
        ordersService.updateOrderProduct(orderProduct);
        return "redirect:/orderDetail/"+orderProduct.getOrders().getId();
    }

    /**
     * 取消申请
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancleGood/{id}",method = RequestMethod.GET)
    public String cancleGood(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        orderProduct.setExitStatus(0);
        Orders orders = orderProduct.getOrders();
        ExitOrders exitOrders = orderProduct.getExitOrders();
        orderProduct.setExitOrders(null);
        orders.setT(0);
        ordersService.deleteExitOrders(exitOrders);
        ordersService.updateOrderProduct(orderProduct);
        ordersService.updateOrders(orders);
        return "redirect:/orderDetail/"+id;
    }
    /**
     * 平台拒绝退货
     * @param id
     * @return
     */
    @RequestMapping(value = "/disagreeExit/{id}",method = RequestMethod.GET)
    public String disagreeExit(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        orderProduct.setExitStatus(9);
        ordersService.updateOrderProduct(orderProduct);
        return "redirect:/orderDetail/"+orderProduct.getOrders().getId();
    }




//    /**
//     * 前台获取退货订单
//     * @param session
//     * @param model
//     * @return
//     */
//    @RequestMapping(value="/exitOrderProduts",method = RequestMethod.GET)
//    public String exitOrderProduts(HttpSession session,Model model){
//        User user =(User)session.getAttribute("loginUser");
//        List<Orders> orderses = ordersService.findOrdersByTAndUserId(1,user.getId());
//        List<OrderPoJo> orderPoJos = new ArrayList<>();
//        for(Orders orders :orderses){
//            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
//            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
//            orderPoJos.add(orderPoJo);
//        }
//        model.addAttribute("orderPoJos",orderPoJos);
//        return "frontStage/User/orderList";
//    }




//    /**
//     * 申请退款（订单尚未发货）
//     * @param id
//     * @param session
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/exitOrder/{id}",method = RequestMethod.GET)
//    public String exitOrder(@PathVariable("id") int id,HttpSession session,Model model){
//        User user =(User)session.getAttribute("loginUser");
//        Orders orders =  ordersService.findOrdersById(id);
//        if(orders !=null && orders.getP()==0){
//            orders.setT(1);
//            orders.setStatus(1);
//            ordersService.updateOrders(orders);
//        }
//        List<Orders> orderses = ordersService.findOrdersByTAndUserId(1,user.getId());
//        model.addAttribute("orderses",orderses);
//        return "frontStage/User/orderList";
//    }

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
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }


    /**
     * 获取已关闭的订单
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/closeOrders",method = RequestMethod.GET)
    public String closeOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByDAndUserId(2,user.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
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
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        if(orderses!=null) {
            for (Orders orders : orderses) {
                List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
                OrderPoJo orderPoJo = new OrderPoJo(orders, orderProducts);
                orderPoJos.add(orderPoJo);
            }
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
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
    List<OrderPoJo> orderPoJos = new ArrayList<>();
    for(Orders orders :orderses){
        List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
        if(orderProducts!=null) {
            log.info(orderProducts.get(0).getDescribes());
        }
        OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
        orderPoJos.add(orderPoJo);
    }
    model.addAttribute("orderPoJos",orderPoJos);
    return "frontStage/User/orderList";
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
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }

    /**
     * 获取已收货订单
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOrders",method = RequestMethod.GET)
    public String achiveOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByPAndUserId(2,user.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
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
        List<OrderProduct> orderProducts = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> os = userService.findOrderProductByOrderId(orders.getId());
            for(OrderProduct orderProduct :os){
                if(orderProduct.getCommentStatus() ==0 &&orderProduct.getExitStatus()==0&&orderProduct.getStauts()==0){
                    log.info("订单项退款状态："+orderProduct.getStauts());
                    log.info("订单项退货状态："+orderProduct.getExitStatus());
                    orderProducts.add(orderProduct);
                }
            }
        }
        model.addAttribute("orderProducts",orderProducts);
        return "frontStage/User/commentList";
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
        List<Orders> orderses = ordersService.findAllByStatusAndUserId(user.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        if(orderses!=null) {
            for (Orders orders : orderses) {
                List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
                OrderPoJo orderPoJo = new OrderPoJo(orders, orderProducts);
                orderPoJos.add(orderPoJo);
            }
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }

    /**
     * 获取退货订单
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/exitGoods",method = RequestMethod.GET)
    public String exitGoods(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByTAndUserId(1,user.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        if(orderses!=null) {
            for (Orders orders : orderses) {
                List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
                OrderPoJo orderPoJo = new OrderPoJo(orders, orderProducts);
                orderPoJos.add(orderPoJo);
            }
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @RequestMapping(value ="/deleteOrders/{id}",method = RequestMethod.GET)
    public String deleteOrders(@PathVariable("id")int id){
        Orders orders = ordersService.findOrdersById(id);
        orders.setD(2);
        ordersService.updateOrders(orders);
        return "redirect:/userOrders";
    }





    @RequestMapping(value ="/areaListOrders",method = RequestMethod.GET)
    public String areaOrders(HttpSession session,Model model){
        Areas areas =(Areas)session.getAttribute("areas");
        List<Orders> orderses = ordersService.findOrdersByAreaId(areas.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }


    @RequestMapping(value ="/roleListOrders",method = RequestMethod.GET)
    public String roleOrders(HttpSession session,Model model){
        Roles roles = (Roles)session.getAttribute("roles");
        List<Orders> orderses = ordersService.findOrdersByRoleId(roles.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/orderList";
    }

    @RequestMapping(value = "/withDraw",method = RequestMethod.GET)
    public String withDraw(){
        return "frontStage/User/Withdraw";
    }

    /**
     * 体现订单
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/withdrawDetail",method = RequestMethod.GET)
    public String profitDetail(HttpSession session,Model model){
        List<WithdrawalsOrder> withdrawalsOrders = new ArrayList<>();
        if(session.getAttribute("roles")!=null){
            Roles roles = (Roles)session.getAttribute("roles");
            withdrawalsOrders = ordersService.findWithdrawalsOrderByRoleId(roles.getId());
            log.info("获取角色体现订单");
        }else if(session.getAttribute("areas")!=null){
            Areas areas = (Areas)session.getAttribute("areas");
            withdrawalsOrders = ordersService.findWithdrawalsOrderByAreaId(areas.getId());
            log.info("获取大区体现订单！");
        }
        model.addAttribute("withdrawalsOrders",withdrawalsOrders);
        return "frontStage/User/withdrawDetail";
    }

    @RequestMapping(value = "/roleCommission",method = RequestMethod.GET)
    public String roleCommission(HttpSession session,Model model){
        List<Orders> orderses = new ArrayList<>();
            Roles roles = (Roles) session.getAttribute("roles");
            orderses = ordersService.findOrdersByRoleId(roles.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders:orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/roleCommission";
    }

    @RequestMapping(value = "/areaCommission",method = RequestMethod.GET)
    public String areaCommission(HttpSession session,Model model){
        List<Orders> orderses = new ArrayList<>();
        Areas areas = (Areas) session.getAttribute("areas");
        orderses = ordersService.findOrdersByAreaId(areas.getId());
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders orders:orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "frontStage/User/areaCommission";
    }



    @RequestMapping(value = "/paySuccess",method = RequestMethod.POST)
    public String getPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                String uuid = (String)map.get("out_trade_no");
                Orders orders = ordersService.findOrdersByUid(uuid);
                Profit profit = terraceService.findProfit();
                //判断订单中有没有角色和大区，如果有，将佣金划分入大区和角色中待结算佣金中
                if(orders.getRoles()!=null) {
                    Roles roles = orders.getRoles();
                    float total = roles.getTotalCommission() + (orders.getTotalPV()*profit.getRole_count())/100;
                    float wait = roles.getWaitCommission() +(orders.getTotalPV()*profit.getRole_count())/100;
                    roles.setTotalCommission(total);
                    roles.setWaitCommission(wait);
                    userService.updateRoles(roles);
                    Areas areas = orders.getAreas();
                    total = areas.getTotalCommission() + (orders.getTotalPV()*profit.getArea_count())/100;
                    wait = areas.getWaitCommission()+(orders.getTotalPV()*profit.getArea_count())/100;
                    areas.setWaitCommission(wait);
                    areas.setTotalCommission(total);
                    userService.updateAreas(areas);
                }
                log.info("更新数据库中订单的信息："+orders.getUuid());
                orders.setPayTime(new Date());
                orders.setF(1);
                ordersService.updateOrders(orders);
                //跟新商品的销量和库存
                ordersService.updateOrderProductAfterPay(orders.getId());


            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 创建提现订单
     * @param cout
     * @param session
     * @return
     */
    @RequestMapping(value = "/witdraw",method =RequestMethod.POST)
    public String withdraw(float cout,HttpSession session){
        WithdrawalsOrder withdrawalsOrder = new WithdrawalsOrder();
        if(session.getAttribute("areas")!=null){

            Areas areas =(Areas) session.getAttribute("areas");
//            if(cout<50) {
//                return "redirect:/withDraw";
//            }
//              else
            if (areas.getExitCommission() < cout) {
                    return "redirect:/withDraw";
                }
            withdrawalsOrder.setUuid(UUID.randomUUID().toString());
            withdrawalsOrder.setAreas(areas);
            withdrawalsOrder.setPrices(cout);
            withdrawalsOrder.setDate(new Date());
            ordersService.addWithdrawalsOrder(withdrawalsOrder);
            float exit = areas.getExitCommission()-cout;
            areas.setExitCommission(exit);
            float total = areas.getTotalCommission()-cout;
            areas.setTotalCommission(total);
            userService.updateAreas(areas);
        }else if(session.getAttribute("roles")!=null){
            Roles roles =(Roles)session.getAttribute("roles");
            if(cout<50){
                return "redirect:/Withdraw";
            } else
              if(roles.getExitCommission()<cout){
                return "redirect:/Withdraw";
            }
            withdrawalsOrder.setUuid(UUID.randomUUID().toString());
            withdrawalsOrder.setRoles(roles);
            withdrawalsOrder.setPrices(cout);
            withdrawalsOrder.setDate(new Date());
            ordersService.addWithdrawalsOrder(withdrawalsOrder);
            float exit = roles.getExitCommission()-cout;
            roles.setExitCommission(exit);
            float total = roles.getTotalCommission()-cout;
            roles.setTotalCommission(total);
            userService.updateRoles(roles);
        }
        return "redirect:/refund/"+withdrawalsOrder.getId();
    }


    @RequestMapping(value ="listOrder",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String listOrder(HttpSession session, Model model){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        List<Orders> orders = userService.listOrders();
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        long time =0;
        long nd = 1000*24*60*60;
        for(Orders order:orders){
            if(order.getSentTime()!=null) {
                if(order.getD()==0&&order.getP()==2) {
                    time = new Date().getTime() - order.getSentTime().getTime();
                    long day = time / nd;
                    log.info("发货时间" + order.getSentTime().getTime() + ",现在时间" + new Date().getTime());
                    log.info("发货后时间：" + day);
                    if (day > 14&&order.getStatus()==0) {
                        order.setD(1);
                    }
                }
            }
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value ="listOrderByF/{f}",method = RequestMethod.GET)
    public String listOrderByF(HttpSession session, Model model,@PathVariable("f") int f){
        List<Orders> orders = userService.listOrdersByF(f);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value ="listOrderByP/{p}",method = RequestMethod.GET)
    public String listOrderByP(HttpSession session, Model model,@PathVariable(value ="p") int p){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        List<Orders> orders = userService.listOrdersByP(p);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value ="listOrderByD/{d}",method = RequestMethod.GET)
    public String listOrderByD(HttpSession session, Model model,@PathVariable("d") int d){
        List<Orders> orders = userService.listOrdersByD(d);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value ="listOrderByT/{t}",method = RequestMethod.GET)
    public String listOrderByT(HttpSession session, Model model,@PathVariable("t") int t){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }

        List<Orders> orders = userService.listOrdersByT(t);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }



    @RequestMapping(value ="listOrderByS",method = RequestMethod.GET)
    public String listOrderByS(HttpSession session, Model model){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }

        List<Orders> orders = userService.listOrdersByStatus();
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value ="listOrderByC/{c}",method = RequestMethod.GET)
    public String listOrderByC(HttpSession session, Model model,@PathVariable("c") int c){
//        if(session.getAttribute("loginTerrace") == null){
//            return "redirect:/loginTerrace";
//        }
        List<Orders> orders = userService.listOrdersByC(c);
        List<OrderPoJo> orderPoJos = new ArrayList<>();
        for(Orders order:orders){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(order.getId());
            OrderPoJo orderPoJo = new OrderPoJo(order,orderProducts);
            orderPoJos.add(orderPoJo);
        }
        model.addAttribute("orderPoJos",orderPoJos);
        return "backStage/Orders/listOrders";
    }

    @RequestMapping(value="editOrderProductNum",method = RequestMethod.GET)
    @ResponseBody
    public String editOrderProductNum(int id,int num){
        log.info(id+"测试"+num);
        OrderProduct orderProduct = userService.findOrderProductById(id);
        Cart cart = orderProduct.getCart();
        Orders orders = orderProduct.getOrders();

        float prices = orderProduct.getPrices()*orderProduct.getCount();
        double total  = cart.getTotalPrices()-prices;
        int count = cart.getCount()-orderProduct.getCount();
        cart.setCount(count);
        cart.setTotalPrices(total);
        orderProduct.setCount(num);
        prices = orderProduct.getPrices()*orderProduct.getCount();
        total = cart.getTotalPrices()+prices;
        cart.setTotalPrices(total);
        count = cart.getCount()+orderProduct.getCount();
        cart.setCount(count);
        cartService.updateCart(cart);
        userService.updateOrderProduct(orderProduct);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","success");
        return jsonObject.toString();
    }

    /**
     * 发货
     * @param id
     * @return
     */
    @RequestMapping(value="sendOrder/{id}",method={RequestMethod.GET,RequestMethod.POST})
    public String sentOrder(@PathVariable("id")int id,String logisticCompany,String logisticCode){
        log.info("发货成功！");
        Orders orders = ordersService.findOrdersById(id);
        orders.setSentTime(new Date());
        orders.setP(1);
        Logistic logistic = terraceService.findLogisticByName(logisticCompany);
        orders.setLogistic(logistic);
        orders.setCarriageCode(logisticCode);
        ordersService.updateOrders(orders);
       return "redirect:/orderDetail/"+id;

    }

    /**
     * 退货发货
     * @param id
     * @return
     */
    @RequestMapping(value = "/sendOrderProduct/{id}",method = RequestMethod.POST)
    public String sendOrderProduct(@PathVariable("id")int id,String logisticCompany,String logisticCode){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        orderProduct.setExitStatus(3);
        ExitOrders exitOrders = orderProduct.getExitOrders();
        exitOrders.setSentTime(new Date());
        Logistic logistic = terraceService.findLogisticByName(logisticCompany);
        exitOrders.setLogistic(logistic);
        exitOrders.setCarriageCode(logisticCode);
        ordersService.updateExitOrders(exitOrders);
        log.info("退货发货，更新订单项的状态");
        log.info("退货发货，更新订单项的状态");
        log.info("退货发货，更新订单项的状态");
        ordersService.updateOrderProduct(orderProduct);
        return "redirect:/exitGoods";
    }

    /**
     * 测试用
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="sendOrderProduct/{id}",method=RequestMethod.GET)
    public String sentOrderProduct(@PathVariable("id")int id,Model model){
        model.addAttribute("id",id);
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        ExitOrders exitOrders = orderProduct.getExitOrders();
        ordersService.updateExitOrders(exitOrders);
        ordersService.updateOrderProduct(orderProduct);
        return "frontStage/User/editLogisticInfo";
    }




//    @RequestMapping(value="agreeExitOrder/{id}",method=RequestMethod.GET)
//    public String agreeExitOrder(@PathVariable("id")int id){
//        Orders orders = ordersService.findOrdersById(id);
//        orders.setT(2);
//        ordersService.updateOrders(orders);
//
//    }


    /**
     * 退货 收货后退款给用户
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/signOrders/{id}",method=RequestMethod.GET)
    public String signOrders(@PathVariable("id")int id) throws Exception {
        //统计要付的钱
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        Orders orders = orderProduct.getOrders();
//        int money = (int)(orders.getPrices()*100);
        int money = (int)(orderProduct.getPrices()*orderProduct.getCount()*100);
        log.info(money);
        User user = orders.getUser();
        String openId =user.getOpenId();
        String nonce_str = WebChatUtil.generateStr(32);
        String ip = "131.25.0.7";

        //组装参数
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("amount", String.valueOf(money));
        map.put("check_name", "NO_CHECK");
        map.put("desc", "拿去花");
        map.put("mch_appid", WebChatUtil.getAPPID());
        map.put("mchid", WebChatUtil.getTenantId());
        map.put("nonce_str", nonce_str);
        map.put("openid", openId);
        map.put("partner_trade_no", orderProduct.getUuid());
        map.put("spbill_create_ip",ip);


        String stringA=XMLUtil.mapToStr(map);
        String stringSignTemp=stringA+"&key="+WebChatUtil.getKEY();
        String sign = MD5.toMD5(stringSignTemp).toUpperCase();

        map.put("sign", sign);
        String xml=XMLUtil.mapToXml(map);

        System.out.println("stringA"+stringA);
        System.out.println("xml-"+xml);
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        CloseableHttpClient client = WebChatUtil.loadCert();

        HttpPost post = new HttpPost(url);
        boolean result=false;
        try{
            String xml2=new String(xml.getBytes("UTF-8"), "ISO8859_1");
            StringEntity s = new StringEntity(xml2);
            s.setContentEncoding("UTF-8");
            s.setContentType("text/xml");
            post.setEntity(s);

            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String responseContent= EntityUtils.toString(entity, "UTF-8");
            Map<String,Object> resultMap = XMLUtil.parseXML(responseContent);
            System.out.println(responseContent);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                if(resultMap.get("result_code").equals("FAIL")){
                    log.info("退货失败");
                }else{
                    log.info("退货成功!");
                    Profit profit = terraceService.findProfit();
                    float prices = orderProduct.getPrices() * orderProduct.getCount();
                    float pv = orderProduct.getPv()*orderProduct.getCount();
                    float totalPv = orders.getTotalPV();
                    int count = orders.getNumber() - orderProduct.getCount();
                    orders.setNumber(count);
                    prices = orders.getPrices() - prices;
                    orders.setPrices(prices);
                    totalPv = totalPv-pv;
                    orders.setTotalPV(totalPv);   //退款后设置总的pv
                    orders.setTotalProfit(prices - totalPv);


                    if(orders.getRoles()!=null) {


                        //更新角色中的佣金
                        Roles roles = orders.getRoles();
                        float totalCommission = roles.getTotalCommission();
                        float waitCommission = roles.getWaitCommission();
                        //总佣金减去订单项佣金
                        totalCommission = totalCommission - (pv * profit.getRole_count())/100;
                        waitCommission = waitCommission - (pv * profit.getRole_count())/100;
                        roles.setWaitCommission(waitCommission);
                        roles.setTotalCommission(totalCommission);
                        userService.updateRoles(roles);

                        //更新大区中的佣金
                        Areas areas = orders.getAreas();
                        totalCommission = areas.getTotalCommission();
                        waitCommission = areas.getWaitCommission();
                        totalCommission = totalCommission - (pv * profit.getArea_count())/100;
                        waitCommission = waitCommission - (pv * profit.getArea_count())/100;
                        areas.setTotalCommission(totalCommission);
                        areas.setWaitCommission(waitCommission);
                        userService.updateAreas(areas);


                    }

                    orderProduct.setExitStatus(4);
                    orders.setStatus(0);
                    orders.setT(2);
                    ordersService.updateOrderProduct(orderProduct);
                    ordersService.updateOrders(orders);
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            return "redirect:/orderDetail/"+orders.getId();
        }
    }


    @RequestMapping(value="/achieveOrder/{id}",method =RequestMethod.GET)
    public String achieveOrder(@PathVariable("id")int id){
        Orders orders = ordersService.findOrdersById(id);
        orders.setP(2);
        ordersService.updateOrders(orders);
        return "redirect:/getOrders";
    }



    @RequestMapping(value = "/commentProduct",method = RequestMethod.POST)
    @ResponseBody
    public String commentProduct(int id,String text,HttpSession session){
        log.info("评论商品ID：=>>>>>>>>>>>>>>>>>>"+id);
        log.info("评论商品内容：=>>>>>>>>>>>>"+text);
        Comment comment = new Comment();
        User user = (User)session.getAttribute("loginUser");
        comment.setUser(user);
        comment.setUsername(user.getUsername());
        comment.setText(text);
        OrderProduct orderProduct =ordersService.findOrderProductById(id);
        comment.setGood_id(orderProduct.getGood_id());
        comment.setOrderProduct(orderProduct);
        comment.setDate(new Date());
        orderProduct.setCommentStatus(1);
        ordersService.updateOrderProduct(orderProduct);
        addressService.addComment(comment);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","success");
        return jsonObject.toString();
    }

}
