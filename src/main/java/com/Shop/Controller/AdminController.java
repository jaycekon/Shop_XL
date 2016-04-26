package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.*;
import com.Shop.Util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Controller
public class AdminController {
    private final static String wechatName ="xiaoguozhushou";
    @Autowired
    private TerraceService terraceService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private OrdersService ordersService;

    Logger log = Logger.getLogger(AdminController.class);

    @RequestMapping(value="backStage")
    public String backStage(){
        return "backStage/index";
    }

    @RequestMapping(value ="indexCarousel")
    public String indexCarousel(Model model){
        List<Image> images = goodService.findImage();
        model.addAttribute("images",images);
        return "backStage/SystemManage/indexCarousel";
    }

    @RequestMapping(value ="getCode")
    public String getCode(HttpServletRequest request){
        String code=request.getParameter("code");
        log.info("获取code成功---------->"+code);
        WebChatUtil.getCode(code,request);
        String openId =(String)request.getSession().getAttribute("openId");
        log.info("获取openId成功------------->"+openId);
        if(terraceService.findAreasByOpenId(openId)!=null){
            log.info("成功获取大区信息！");
            Areas areas = terraceService.findAreasByOpenId(openId);
            request.getSession().setAttribute("areas",areas);
            return "frontStage/User/AreaCenter";
        }else if(terraceService.findRolesByOpenId(openId)!=null){
            log.info("成功获取角色信息！");
            Roles roles = terraceService.findRolesByOpenId(openId);
            request.getSession().setAttribute("roles",roles);
            return "frontStage/User/RoleCenter";
        }else{
            log.info("成功获取角色信息！");
            User user = terraceService.findUseByOpenId(openId);
            if(user==null){
            }
            request.getSession().setAttribute("loginUser",user);
            return "redirect:/index";
        }
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

    /**
     * 更新 倾销币费用，会员认证费用，倾销币价格
     * @param model
     * @return
     */
    @RequestMapping(value = "updateProfit",method = RequestMethod.GET)
    public String updateProfit(Model model){
        Profit profit = terraceService.findProfit();
        model.addAttribute(profit);
        return "backStage/SystemManage/parameter";
    }
    @RequestMapping(value = "updateProfit",method = RequestMethod.POST)
    public String updateProfit(Profit profit,Model model){
        Profit p = terraceService.findProfit();
        p.setArea_count(profit.getArea_count());
        p.setCountPrices(profit.getCountPrices());
        p.setDumpingCount(profit.getDumpingCount());
        p.setRecordPrices(profit.getRecordPrices());
        p.setRole_count(profit.getRole_count());
        terraceService.updateProfit(p);
        model.addAttribute(profit);
        return "backStage/SystemManage/parameter";
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

    @RequestMapping(value ="orderDetail/{id}",method = RequestMethod.GET)
    public String orderDetail(@PathVariable(value ="id")int id,Model model){
        Orders orders = userService.findOrdersById(id);
        List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
        OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
        model.addAttribute("orderPojo",orderPoJo);
        return "backStage/Orders/orderDetail";
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

    @RequestMapping(value ="orderCheck",method = RequestMethod.GET)
    public String checkOrder(){
        return "backStage/Orders/orderCheck";
    }

    @RequestMapping(value ="orderCheck",method = RequestMethod.POST)
    public String checkOrder(int id,Model model){
        Orders orders = userService.findOrdersById(id);
        if(orders == null){
            model.addAttribute("orderPoJo",null);
            return "backStage/Orders/orderCheck";
        }
        List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
        OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
        model.addAttribute("orderPoJo",orderPoJo);
        return "backStage/Orders/orderCheck";
    }







    @RequestMapping(value = "myCrod",method =RequestMethod.GET)
    public String myCrod(Model model){
        int flag =(int)((1+Math.random())*10000);
        String code = WebChatUtil.getQrCodePic(9,flag);
        log.info("申请生成角色二维码成功："+code);
        model.addAttribute("code",code);
        return "frontStage/code";
    }

    @RequestMapping(value = "roleCrod/{id}",method =RequestMethod.GET)
    public String roleCrod(@PathVariable(value ="id") int id,Model model){
        String code = WebChatUtil.getQrCodePic(1,id);
        log.info("申请生成角色二维码成功："+code);
        model.addAttribute("code",code);
        return "frontStage/code";
    }
    @RequestMapping(value = "userCrod/{id}",method =RequestMethod.GET)
    public String userCrod(@PathVariable(value ="id") int id,Model model){
        String code = WebChatUtil.getQrCodePic(2,id);
        log.info("申请生成店家二维码成功："+code);
        model.addAttribute("code",code);
        return "frontStage/code";
    }

    @RequestMapping(value ="addTopArea",method =RequestMethod.GET)
    public String addTopArea(){
        return "";
    }

    @RequestMapping(value ="addTopArea",method =RequestMethod.POST)
    public String addTopArea(Area area){
        addressService.addArea(area);
        return "";
    }

    @RequestMapping(value ="addSecondArea",method =RequestMethod.GET)
    public String addSecondArea(Model model){
        List<Area> areas = addressService.findTopArea();
        model.addAttribute("areas",areas);
        return "";
    }

    @RequestMapping(value ="addSecondArea",method =RequestMethod.POST)
    public String addSecondArea(Area area,String area_name){
        Area a = addressService.findAreaByAreaName(area_name);
        area.setArea(a);
        addressService.addArea(area);
        return "";
    }


    @RequestMapping(value = "setImage/{id}",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String addGoodDown(@RequestParam("files")MultipartFile files,@PathVariable("id")int id){
        String fileNames = UUID.randomUUID().toString()+".jpg";
        String file = "http://115.29.141.108/Shop_XL_war/"+fileNames;
        Image image = goodService.findImageById(id);
        String path= File.separator+"var"+File.separator+"www"+File.separator+"html"+File.separator+"Shop_XL_war"+File.separator;
                try {
                    FileUtils.copyInputStreamToFile(files.getInputStream(),new File(path,fileNames));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                image.setAddress(file);
                log.info(file);
                goodService.addImage(image);
        return "redirect:/indexCarousel";
    }

    @RequestMapping(value="/getOrderLogisticTrack/{id}",method={RequestMethod.GET,RequestMethod.POST})
    public String getOrderLogisticTrack(@PathVariable("id")int id, HttpSession session,Model model){
        Orders orders = ordersService.findOrdersById(id);
        String json = ExpressSearch.consult3(orders.getLogistic().getLogis_comp_id(),orders.getCarriageCode());
        ExpressBean  expressBean = (ExpressBean) DataConvertorUtil.json2object(json, ExpressBean.class);
        model.addAttribute("expressBean", expressBean);
        model.addAttribute("orders",orders);
        System.out.println("getOrderLogisticTrack   expressBean : "+expressBean);
        return "frontStage/User/logistics";
    }

    @RequestMapping(value="/getLogisticTrack/{id}",method={RequestMethod.GET,RequestMethod.POST})
    public String getLogisticTrack(@PathVariable("id")int id, HttpSession session,Model model){
        Orders orders = ordersService.findOrdersById(id);
        String json = ExpressSearch.consult3(orders.getLogistic().getLogis_comp_id(),orders.getCarriageCode());
        ExpressBean  expressBean = (ExpressBean) DataConvertorUtil.json2object(json, ExpressBean.class);
        model.addAttribute("expressBean", expressBean);
        System.out.println("getOrderLogisticTrack   expressBean : "+expressBean);
        return "backStage/Orders/logisticsTrace";
    }


    @RequestMapping(value="/getOrderProductLogisticTrack/{id}",method={RequestMethod.GET,RequestMethod.POST})
    public String getOrderProductLogisticTrack(@PathVariable("id")int id, HttpSession session,Model model){
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        ExitOrders exitOrders = orderProduct.getExitOrders();
        String json = ExpressSearch.consult3(exitOrders.getLogistic().getLogis_comp_id(),exitOrders.getCarriageCode());
        ExpressBean  expressBean = (ExpressBean) DataConvertorUtil.json2object(json, ExpressBean.class);
        model.addAttribute("expressBean", expressBean);
        model.addAttribute("exitOrders",exitOrders);
        System.out.println("getOrderLogisticTrack   expressBean : "+expressBean);
        return "frontStage/User/logistics";
    }


//    @RequestMapping(value="/getProductLogisticTrack/{id}",method={RequestMethod.GET,RequestMethod.POST})
//    public String getProductLogisticTrack(@PathVariable("id")int id, HttpSession session,Model model){
//        Orders orders = ordersService.findOrdersById(id);
//        OrderProduct orderProduct = ordersService.findOrderProductById(id);
//        ExitOrders exitOrders = orderProduct.getExitOrders();
//
//        String json = ExpressSearch.consult3(exitOrders.getLogistic().getLogis_comp_id(),exitOrders.getCarriageCode());
//        ExpressBean  expressBean = (ExpressBean) DataConvertorUtil.json2object(json, ExpressBean.class);
//        model.addAttribute("expressBean", expressBean);
//        model.addAttribute("exitOrders",exitOrders);
//        System.out.println("getOrderLogisticTrack   expressBean : "+expressBean);
//        return "backStage/Orders/logisticsTrace";
//    }

}
