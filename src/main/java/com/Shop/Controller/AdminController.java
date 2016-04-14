package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.Shop.Util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    Logger log = Logger.getLogger(AdminController.class);

    @RequestMapping(value ="getInfo",method = RequestMethod.POST)
    public void getInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        //验证服务器地址
//		String signature=request.getParameter("signature");
//		String timestamp = request.getParameter("timestamp");
//		String nonce=request.getParameter("nonce");
//		String echostr=request.getParameter("echostr");
//		PrintWriter out = response.getWriter();
//
//		if(AccessTokenUtil.checkSignature(signature,timestamp,nonce)){
//            log.info("check success");
//			out.write(echostr);
//			out.flush();
//			out.close();
//		}
        BufferedReader reader = request.getReader();
        String line ="";
        StringBuffer inputString = new StringBuffer();
        while((line = reader.readLine())!=null){
            inputString.append(line);
        }
        request.getReader().close();
        PrintWriter outp = response.getWriter();
        Map<String, Object> map = XMLUtil.parseXML(inputString.toString());
        String openId = (String)map.get("FromUserName");
        String msgType = (String)map.get("MsgType");
        switch (msgType){
            case "text":  //文本消息
                TextMessage tm = new TextMessage();
                String content = (String)map.get("Content");
                switch(content){
                    case "生成大区二维码":
                        tm.setFromUserName(wechatName);
                        tm.setToUserName((String)map.get("FromUserName"));
                        tm.setCreateTime(String.valueOf(new Date().getTime()));
                        tm.setMsgType("text");
                        int flag =(int)((1+Math.random())*10000);
                        tm.setContent(AccessTokenUtil.getQrCodePic(9,flag));
                        break;
                    case "生成角色二维码":
                        tm.setFromUserName(wechatName);
                        tm.setToUserName((String)map.get("FromUserName"));
                        tm.setCreateTime(String.valueOf(new Date().getTime()));
                        tm.setMsgType("text");
                        Areas areas = terraceService.findAreasByOpenId(openId);
                        if(areas !=null){
                            tm.setContent(AccessTokenUtil.getQrCodePic(1,areas.getId()));
                        }else{
                            tm.setContent("抱歉 您没有权利生成角色二维码！");
                        }
                        break;
                    case "生成店家二维码":
                        tm.setFromUserName(wechatName);
                        tm.setToUserName((String)map.get("FromUserName"));
                        tm.setCreateTime(String.valueOf(new Date().getTime()));
                        tm.setMsgType("text");
                        Roles roles = terraceService.findRolesByOpenId(openId);
                        if(roles !=null){
                            tm.setContent(AccessTokenUtil.getQrCodePic(2,roles.getId()));
                        }else{
                            tm.setContent("抱歉 您没有权利生成店家二维码！");
                        }
                        break;
                    case "商城":
                        tm.setFromUserName(wechatName);
                        tm.setToUserName((String)map.get("FromUserName"));
                        tm.setCreateTime(String.valueOf(new Date().getTime()));
                        tm.setMsgType("text");
                        tm.setContent("http://weijiehuang.productshow.cn/");
                        HttpSession session = request.getSession();
                        Areas area = terraceService.findAreasByOpenId(openId);
                        session.setAttribute("area",area);
                        break;
                }
                outp.write(XMLUtil.toXML(tm));
                outp.flush();
                outp.close();
                break;
            case "event":
                String event = (String)map.get("Event");
                switch(event){
                    case "subscribe":
                        String t =map.get("EventKey").toString().replace("qrscene_","");
                        JsonObject json = UserInfoUtil.getUserInfo(openId);
                        Long oi = Long.parseLong(t);
                        log.info("用户关注公众号!");
                        if(!terraceService.checkOpenId(openId)){
                            sendText(openId,"用户已存在数据库中："+json.get("nickname").getAsString());
                        }
                        String flags = t.substring(0,1);
                        int flag = Integer.valueOf(flags);
                        if(flag == 9){
                            flags = t.replaceFirst("9","");
                            Areas areas = new Areas();
                            areas.setOpenId(openId);
                            String nickname = new String(json.get("nickname").getAsString());
                            areas.setName(nickname);
                            areas.setImg(json.get("headimgurl").getAsString());

                            areas.setFlag(Long.parseLong(flags));
                            Areas a = terraceService.findAreasByFlag(oi);
                            if(a!=null){
                                sendText(openId,"该二维码已失效，你已成为普通用户："+areas.getName());
                                break;
                            }
                            if(!userService.addArea(areas)){
                                sendText(openId,"用户已成为大区，用户名："+areas.getName());
                                break;
                            }
                            if(map.get("Ticket")!=null){
                                sendText(openId,"有一个用户通过你的二维码关注平台，用户名："+areas.getName());
                            }
                        }else if(flag == 1){
                            flags = t.replaceFirst("1","");
                            Areas areas = terraceService.findAreasById(Integer.parseInt(flags));
                            Roles roles = new Roles();
                            roles.setOpenId(openId);
                            roles.setName(json.get("nickname").getAsString());
                            roles.setImg(json.get("headimgurl").getAsString());
                            roles.setAreas(areas);
                            Roles role = terraceService.findRolesByOpenId(openId);
                            if(role !=null){
                                sendText(areas.getOpenId(),"角色通过扫面您的二维码已成为用户，角色名："+roles.getName());
                                sendText(openId,"恭喜你称为角色，您的大区："+areas.getName());
                                break;
                            }else{
                                sendText(areas.getOpenId(),"角色通过扫面您的二维码已成为用户，角色名："+roles.getName());
                                sendText(openId,"恭喜你称为角色，您的大区："+areas.getName());
                                userService.addRoles(roles);
                            }
                            if(map.get("Ticket")!=null){
                                sendText(openId,"有一个角色通过你的二维码关注平台，用户名："+roles.getName());
                            }
                        }else if(flag == 2){
                            flags = t.replaceFirst("2","");
                            Roles roles = terraceService.findROlesById(Integer.parseInt(flags));
                            User user = new User();
                            user.setOpenId(openId);
                            user.setUsername(json.get("nickname").getAsString());
                            user.setRoles(roles);
                            user.setImg(json.get("headimgurl").getAsString());
                            userService.addUser(user);
                            if(map.get("Ticket")!=null){
                                String referee = openId;
                                sendText(roles.getOpenId(),"用户通过扫面您的二维码已成为用户，用户名："+user.getUsername());
                                sendText(openId,"恭喜你称为用户，您的销售员："+roles.getName());
                            }
                        }


                        break;
                }
        }
    }

    @RequestMapping(value ="getCode")
    public String getCode(HttpServletRequest request){
        String code=request.getParameter("code");
        log.info("获取code成功---------->"+code);
        AccessTokenUtil.getCode(code,request);
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
        return null;
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
        return null;
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

    @RequestMapping(value ="listOrderByP/{p}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
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

    @RequestMapping(value ="listOrderByD",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
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

    @RequestMapping(value ="listOrderByT",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
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


    public static void initMenu(){
        Logger log = Logger.getLogger(AdminController.class);
        String jsonStr=" {\"button\":[";
        jsonStr+=",{\"type\":\"view\",\"name\":\"商城\",\"url\":\"http://weijiehuang.productshow.cn/\"}]}";
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+AccessTokenUtil.getAccessToken();
        log.info(url);
        System.out.println("访问url!");
        post(url,jsonStr);
    }

    //发送文本消息
    private static void sendText(String toUser,String content){
        String strJson = "{\"touser\" :\""+toUser+"\",";
        strJson += "\"msgtype\":\"text\",";
        strJson += "\"text\":{";
        strJson += "\"content\":\""+content+"\"";
        strJson += "}}";
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+AccessTokenUtil.getAccessToken();
        post(url,strJson);
    }

    //用post的方法像指定url发送json请求
    private static boolean post(String url, String json)
    {
        Logger log = Logger.getLogger(AdminController.class);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        boolean result=false;
        try
        {
            String json2=new String(json.getBytes("UTF-8"), "ISO8859_1");
            StringEntity s = new StringEntity(json2);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);

            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String responseContent= EntityUtils.toString(entity, "UTF-8");
            JsonParser jsonparer = new JsonParser();
            JsonObject json1 = jsonparer.parse(responseContent).getAsJsonObject();
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                if (json1.get("errcode").toString() .equals("-1") )
                {
                    log.info(json1.get("errcode").toString());
                }
                else
                {
                    System.out.println("成功！");
                    log.info("成功");
                    result=true;
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }


    @RequestMapping(value = "myCrod",method =RequestMethod.GET)
    public String myCrod(Model model){
        int flag =(int)((1+Math.random())*10000);
        String code = AccessTokenUtil.getQrCodePic(9,flag);
        log.info("申请生成角色二维码成功："+code);
        model.addAttribute("code",code);
        return "frontStage/code";
    }

    @RequestMapping(value = "roleCrod/{id}",method =RequestMethod.GET)
    public String roleCrod(@PathVariable(value ="id") int id,Model model){
        String code = AccessTokenUtil.getQrCodePic(1,id);
        log.info("申请生成角色二维码成功："+code);
        model.addAttribute("code",code);
        return "frontStage/code";
    }
    @RequestMapping(value = "userCrod/{id}",method =RequestMethod.GET)
    public String userCrod(@PathVariable(value ="id") int id,Model model){
        String code = AccessTokenUtil.getQrCodePic(2,id);
        log.info("申请生成店家二维码成功："+code);
        model.addAttribute("code",code);
        return "frontStage/code";
    }



}
