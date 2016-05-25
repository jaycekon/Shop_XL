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

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private ExpressBean expressBean;

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
    public String getCode(HttpServletRequest request, HttpServletResponse response){

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
        }else if(terraceService.findUseByOpenId(openId)!=null){
            log.info("成功获取角色信息！");
            User user = terraceService.findUseByOpenId(openId);
            request.getSession().setAttribute("loginUser",user);
            log.info("这里要出问题了！！！！");
            return "redirect:/index";
        }else{
            User user = new User();
            JsonObject jsonObject = UserInfoUtil.getUserInfo(openId);
            user.setOpenId(openId);
            user.setUsername(jsonObject.get("nickname").getAsString());
            user.setImg(jsonObject.get("headimgurl").getAsString());
            userService.addUser(user);
            request.getSession().setAttribute("loginUser",user);
            return  "redirect:/index";
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
           return "redirect:/backStage";
        }
    }
    @RequestMapping(value="logOut",method = RequestMethod.GET)
    public String logOut(HttpSession session){
        session.setAttribute("loginTerrace",null);
        return "backStage/login";
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
        p.setLevel1(profit.getLevel1());
        p.setLevel1Rate(profit.getLevel1Rate());
        p.setLevel2(profit.getLevel2());
        p.setLevel2Rate(profit.getLevel2Rate());
        p.setLevel3Rate(profit.getLevel3Rate());
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
        Profit profit = terraceService.findProfit();
        List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
        OrderPoJo orderPoJo = new OrderPoJo(orders,orderProducts);
        List<Logistic> logistics = terraceService.listLogistic();
        model.addAttribute("orderPojo",orderPoJo);
        model.addAttribute("logistics",logistics);
        model.addAttribute("profit",profit);
        return "backStage/Orders/orderDetail";
    }

    @RequestMapping(value ="listAreas",method = RequestMethod.GET)
    public String listAreas(Model model){
        Page<Areas> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(0);
        List<Areas> areas = userService.listAreas();
        page.setTotalCount(areas.size());
        List<Areas> a = userService.listAreasByPage(page);
        page.setList(a);
        model.addAttribute("page",page);
        return "backStage/User/listArea";
    }

    @RequestMapping(value ="listAreas/{pages}",method = RequestMethod.GET)
    public String listAreas(Model model,@PathVariable("pages") int pages){
        Page<Areas> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(pages);
        List<Areas> areas = userService.listAreas();
        for(Areas areas1:areas){
            List<Roles> roles = userService.listRolesByAreas(areas1.getId());
            areas1.setCount(roles.size());
            userService.updateAreas(areas1);
        }
        page.setTotalCount(areas.size());
        List<Areas> a = userService.listAreasByPage(page);
        page.setList(a);
        model.addAttribute("page",page);
        return "backStage/User/listArea";
    }

    @RequestMapping(value ="listRoles",method = RequestMethod.GET)
    public String listRoles(Model model){
        Profit profit = terraceService.findProfit();
        Page<Roles> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(0);
        List<Roles> roles = userService.listRoles();
        for(Roles role:roles) {
            List<User> users = userService.listUserByRolesId(role.getId());
            role.setCount(users.size());
            if(users.size() < profit.getLevel1()){
                role.setLevel(1);
                role.setRates(profit.getLevel1Rate());
            }else if(users.size() <profit.getLevel2()){
                role.setLevel(2);
                role.setRates(profit.getLevel2Rate());
            }else{
                role.setLevel(3);
                role.setRates(profit.getLevel3Rate());
            }
            userService.updateRoles(role);
        }
        page.setTotalCount(roles.size());
        page.setList(userService.listRolesByPage(page));
        model.addAttribute("page",page);
        return "backStage/User/listRoles";
    }

    @RequestMapping(value ="listRoles/{pages}",method = RequestMethod.GET)
    public String listRoles(Model model,@PathVariable("pages")int pages){
        Page<Roles> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(pages);
        List<Roles> roles = userService.listRoles();
        page.setTotalCount(roles.size());
        page.setList(userService.listRolesByPage(page));
        model.addAttribute("page",page);
        return "backStage/User/listRoles";
    }


    @RequestMapping(value ="listUser",method = RequestMethod.GET)
    public String listUser(Model model){
        Page<User> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(0);
        List<User> users = userService.listUser();
        page.setTotalCount(users.size());
        page.setList(userService.listUserByPage(page));
        model.addAttribute("page",page);
        return "backStage/User/listUser";
    }

    @RequestMapping(value ="listUser/{pages}",method = RequestMethod.GET)
    public String listUser(Model model,@PathVariable("pages")int pages){
        Page<User> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(pages);
        List<User> users = userService.listUser();
        page.setTotalCount(users.size());
        page.setList(userService.listUserByPage(page));
        model.addAttribute("page",page);
        return "backStage/User/listUser";
    }


    /**
     * 显示所有商品，从数据库中获取所有商品
     * @param model
     * @return
     */
    @RequestMapping(value = "listGood",method = RequestMethod.GET)
    public String listGood(Model model){
        Page<Good> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(0);
        List<Good> goods = goodService.listGood();
        page.setTotalCount(goods.size());
        page.setList(goodService.listGoodByPage(page));
        model.addAttribute("page",page);
        return "backStage/Product/productList";
    }

    @RequestMapping(value = "listGood/{pages}",method = RequestMethod.GET)
    public String listGood(Model model,@PathVariable("pages")int pages){
        Page<Good> page = new Page();
        page.setEveryPage(10);
        page.setBeginIndex(pages);
        List<Good> goods = goodService.listGood();
        page.setTotalCount(goods.size());
        page.setList(goodService.listGoodByPage(page));
        model.addAttribute("page",page);
        return "backStage/Product/productList";
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
        if(image ==null){
            image = new Image();
            image.setAddress(file);
            goodService.addImage(image);
        }else{
            image.setAddress(file);
            goodService.updateImage(image);
        }
        String path= File.separator+"var"+File.separator+"www"+File.separator+"html"+File.separator+"Shop_XL_war"+File.separator;
                try {
                    FileUtils.copyInputStreamToFile(files.getInputStream(),new File(path,fileNames));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                log.info(file);

        return "redirect:/indexCarousel";
    }

    @RequestMapping(value="/getOrderLogisticTrack/{id}",method={RequestMethod.GET,RequestMethod.POST})
    public String getOrderLogisticTrack(@PathVariable("id")int id, HttpSession session,Model model){
        Orders orders = ordersService.findOrdersById(id);
        String json = ExpressSearch.consult3(orders.getLogistic().getLogis_comp_id(),orders.getCarriageCode());
        ExpressBean  expressBean =  (ExpressBean) DataConvertorUtil.json2object(json, ExpressBean.class);
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

    @RequestMapping(value="listMember",method = RequestMethod.GET)
    public String test(Model model){
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        List<CountOrder> countOrders  =userService.listCountOrderByType("会员认证");
        page.setTotalCount(countOrders.size());
        countOrders = userService.listCountOrderByTypeAndPage("会员认证",page);
        page.setList(countOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/taxList";

    }


    @RequestMapping(value="listMember/{page}",method = RequestMethod.GET)
    public String test(Model model,@PathVariable("page")int pages){
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<CountOrder> countOrders  =userService.listCountOrderByType("会员认证");
        page.setTotalCount(countOrders.size());
        countOrders = userService.listCountOrderByTypeAndPage("会员认证",page);
        page.setList(countOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/taxList";

    }


    @RequestMapping(value="listCharge",method = RequestMethod.GET)
    public String chargeList(Model model){
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        List<CountOrder> countOrders  =userService.listCountOrderByType("充值");
        page.setTotalCount(countOrders.size());
        countOrders = userService.listCountOrderByTypeAndPage("充值",page);
        page.setList(countOrders);
        log.info("充值列表信息数量："+countOrders.size());
        model.addAttribute("page",page);
        return "backStage/financingManage/chargeList";
    }

    @RequestMapping(value="listCharge/{page}",method = RequestMethod.GET)
    public String chargeList(Model model,@PathVariable("page")int pages){
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<CountOrder> countOrders  =userService.listCountOrderByType("充值");
        page.setTotalCount(countOrders.size());
        countOrders = userService.listCountOrderByTypeAndPage("充值",page);
        page.setList(countOrders);
        log.info("充值列表信息数量："+countOrders.size());
        model.addAttribute("page",page);
        return "backStage/financingManage/chargeList";
    }


    @RequestMapping(value="listMoney",method = RequestMethod.GET)
    public String orderList(Model model){
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        List<Orders> orderses = userService.listOrders();
        page.setTotalCount(orderses.size());
        orderses = userService.listOrdersByPage(page);
        page.setList(orderses);
        Profit profit = terraceService.findProfit();
        model.addAttribute("profit",profit);
        model.addAttribute("page",page);
        return "backStage/financingManage/orderList";
    }

    @RequestMapping(value="listMoney/{page}",method = RequestMethod.GET)
    public String orderList(Model model,@PathVariable("page")int pages){
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<Orders> orderses = userService.listOrders();
        page.setTotalCount(orderses.size());
        orderses = userService.listOrdersByPage(page);
        page.setList(orderses);
        Profit profit = terraceService.findProfit();
        model.addAttribute("profit",profit);
        model.addAttribute("page",page);
        return "backStage/financingManage/orderList";
    }

    @RequestMapping(value="listRoleCommission",method = RequestMethod.GET)
    public String roleCommissionWithdraw(Model model){
        model.addAttribute("status","0");
        model.addAttribute("flag","9");
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRole();
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRoleAndPage(page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }

    @RequestMapping(value="listRoleCommission/{page}",method = RequestMethod.GET)
    public String roleCommissionWithdraw(Model model,@PathVariable("page")int pages){
        model.addAttribute("status","0");
        model.addAttribute("flag","9");
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRole();
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRoleAndPage(page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }
    @RequestMapping(value="listAreaCommission",method = RequestMethod.GET)
    public String AreaCommissionWithdraw(Model model){
        model.addAttribute("status","1");
        model.addAttribute("flag","9");
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreas();
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreasAndPage(page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }

    @RequestMapping(value="listAreaCommission/{page}",method = RequestMethod.GET)
    public String AreaCommissionWithdraw(Model model,@PathVariable("page")int pages){
        model.addAttribute("status","1");
        model.addAttribute("flag","9");
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreas();
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreasAndPage(page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }

    @RequestMapping(value="commissionWithdraw/{id}",method = RequestMethod.GET)
    public String commissionWithdraw(@PathVariable("id")int id){
        WithdrawalsOrder withdrawalsOrder = ordersService.findWithdrawalsOrderById(id);
        return "redirect:/refund/"+withdrawalsOrder.getId();
    }

    @RequestMapping(value="refuseWithdraw/{id}",method = RequestMethod.GET)
    public String refuseWithdraw(@PathVariable("id")int id){
        WithdrawalsOrder withdrawalsOrder = ordersService.findWithdrawalsOrderById(id);
        withdrawalsOrder.setCommitDate(new Date());
        withdrawalsOrder.setStatus(2);
        ordersService.updateWithdrawalsOrder(withdrawalsOrder);
        if(withdrawalsOrder.getRoles()!=null) {
            return "redirect:/listRoleCommission";
        }else{
            return "redirect:/listAreaCommission";
        }
    }

    @RequestMapping(value="listAreaCommissionStatus",method = RequestMethod.GET)
    public String AreaCommissionWithdraw(int status,Model model){
        model.addAttribute("status","1");
        model.addAttribute("flag",status);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreasAndStatus(status);
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreasAndStatusAndPage(status,page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }

    @RequestMapping(value="listAreaCommissionStatus/{page}",method = RequestMethod.GET)
    public String AreaCommissionWithdraw(int status,Model model,@PathVariable("page")int pages){
        model.addAttribute("status","1");
        model.addAttribute("flag",status);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreasAndStatus(status);
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByAreasAndStatusAndPage(status,page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }


    @RequestMapping(value="listRoleCommissionStatus",method = RequestMethod.GET)
    public String RoleCommissionWithdraw(int status,Model model){
        model.addAttribute("status","0");
        model.addAttribute("flag",status);
        Page page = new Page();
        page.setBeginIndex(0);
        page.setEveryPage(10);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRolesAndStatus(status);
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRolesAndStatusAndPage(status,page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }

    @RequestMapping(value="listRoleCommissionStatus/{page}",method = RequestMethod.GET)
    public String RoleCommissionWithdraw(int status,Model model,@PathVariable("page")int pages){
        model.addAttribute("status","0");
        model.addAttribute("flag",status);
        Page page = new Page();
        page.setBeginIndex(pages);
        page.setEveryPage(10);
        List<WithdrawalsOrder> withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRolesAndStatus(status);
        page.setTotalCount(withdrawalsOrders.size());
        withdrawalsOrders = ordersService.findAllWithdrawalsOrderByRolesAndStatusAndPage(status,page);
        page.setList(withdrawalsOrders);
        model.addAttribute("page",page);
        return "backStage/financingManage/memberCommissionWithdraw";
    }


    /**
     * 更新大区的比率
     * @param id
     * @param rates
     * @return
     */
    @RequestMapping(value="updateAreasProfit",method = RequestMethod.POST)
    public String updateAreasProfit(int id,int rates){
        Areas areas = userService.getAreas(id);
        areas.setRates(rates);
        userService.updateAreas(areas);
        return "redirect:/listAreas";
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
