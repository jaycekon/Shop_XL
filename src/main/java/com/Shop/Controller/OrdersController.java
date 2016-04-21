package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.OrdersService;
import com.Shop.Service.UserService;
import com.Shop.Util.OrderPoJo;
import com.Shop.Util.XMLUtil;
import com.google.gson.Gson;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/15.
 */
@Controller
public class OrdersController {
    @Autowired
    UserService userService;
    @Autowired
    OrdersService ordersService;
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
     * 订单项申请退货
     * @param id
     * @return
     */
    @RequestMapping(value = "/exitProduct/{id}",method = RequestMethod.GET)
    public String exitOrderProduct(@PathVariable("id")int id){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        Orders orders = orderProduct.getOrders();
        if(orderProduct !=null){
            if(orderProduct.getStauts()==1){
                orders.setT(0);
                orderProduct.setStauts(0);
            }else if(orderProduct.getStauts()==0){
                orders.setT(1);
                orderProduct.setStauts(1);
            }
            ordersService.updateOrders(orders);
            ordersService.updateOrderProduct(orderProduct);
        }
        return "redirect:/exitOrders";
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
        return "frontStage/User/orderList";
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
        for(Orders orders :orderses){
            List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
            OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
            orderPoJos.add(orderPoJo);
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
     * 获取待评价订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/reCommentOrders",method = RequestMethod.GET)
    public String reCommentOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByCAndUserId(0,user.getId());
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
     * 获取申请退款订单（前台）
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/exitOrders",method = RequestMethod.GET)
    public String exitOrders(HttpSession session,Model model){
        User user =(User)session.getAttribute("loginUser");
        List<Orders> orderses = ordersService.findOrdersByTAndUserId(1,user.getId());
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

    /**
     * 申请退款
     * @param id
     * @return
     */
    @RequestMapping(value ="/exitOrders/{id}",method = RequestMethod.GET)
    public String exitOrders(@PathVariable("id")int id){
        Orders orders = ordersService.findOrdersById(id);
        orders.setT(1);
        ordersService.updateOrders(orders);
        return "redirect:/exitOrders";
    }


    /**
     * 取消申请退款
     * @param id
     * @return
     */
    @RequestMapping(value ="/cancleOrders/{id}",method = RequestMethod.GET)
    public String cancleOrders(@PathVariable("id")int id){
        Orders orders = ordersService.findOrdersById(id);
        orders.setT(0);
        ordersService.updateOrders(orders);
        return "redirect:/resendOrders";
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
                if(orders.getRoles()!=null) {
                    Roles roles = orders.getRoles();
                    float total = roles.getTotalCommission() + orders.getRolesProfit();
                    float wait = roles.getWaitCommission() +orders.getRolesProfit();
                    roles.setTotalCommission(total);
                    roles.setWaitCommission(wait);
                    userService.updateRoles(roles);
                    Areas areas = orders.getAreas();
                    total = areas.getTotalCommission() + orders.getAreaProfit();
                    wait = areas.getWaitCommission()+orders.getAreaProfit();
                    areas.setWaitCommission(wait);
                    areas.setTotalCommission(total);
                    userService.updateAreas(areas);
                }
                log.info("更新数据库中订单的信息："+orders.getUuid());
                orders.setF(1);
                ordersService.updateOrders(orders);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/witdraw",method =RequestMethod.POST)
    public String withdraw(float cout,HttpSession session){
        WithdrawalsOrder withdrawalsOrder = new WithdrawalsOrder();
        if(session.getAttribute("areas")!=null){

            Areas areas =(Areas) session.getAttribute("areas");
            if(cout<50) {
                return "redirect:/Withdraw";
            }
              else  if (areas.getExitCommission() < cout) {
                    return "redirect:/Withdraw";
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
            } else if(roles.getExitCommission()<cout){
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
        for(Orders order:orders){
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

}
