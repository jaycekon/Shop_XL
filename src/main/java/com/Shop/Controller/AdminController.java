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

import javax.management.relation.Role;
import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

    @RequestMapping(value ="getInfo",method = RequestMethod.POST)
    public void getInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Logger log = Logger.getLogger(AdminController.class);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("开始进入后台管理");
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
                tm.setFromUserName(wechatName);
                tm.setToUserName((String)map.get("FromUserName"));
                tm.setCreateTime(String.valueOf(new Date().getTime()));
                tm.setMsgType("text");
                tm.setContent("欢迎"+map.get("FromUserName")+"关注百城倾销网");
                System.out.println(XMLUtil.toXML(tm));
                outp.write(XMLUtil.toXML(tm));
                outp.flush();
                outp.close();
                break;
            case "event":
                String event = (String)map.get("Event");
                switch(event){
                    case "subscribe":
                        String t =map.get("EventKey").toString().replace("qrscene_","");
                        String flags = t.substring(0,1);
                        int flag = Integer.valueOf(flags);
                        JsonObject json = UserInfoUtil.getUserInfo(openId);
                        log.info("用户关注公众号!");
                        if(flag == 0){
                            Areas areas = new Areas();
                            areas.setOpenId(openId);
                            String nickname = new String(json.get("nickname").getAsString());
                            byte[] t_utf = nickname.getBytes("utf-8");
                            String name = new String(t_utf,"utf-8");
                            System.out.println(name);
                            System.out.println(nickname);
                            areas.setName(name);

                            Long oi = Long.parseLong(t.replaceFirst("1",""));
                            areas.setFlag(oi);
//                            if(terraceService.findAreasByFlag(oi) !=null){
//                                Areas a = terraceService.findAreasByFlag(oi);
//                                System.out.println(a.getName());
//                            }else{
                                userService.addArea(areas);
//                            }
                            if(map.get("Ticket")!=null){
                                String referee = openId;
                                System.out.println(areas.getOpenId());
                                System.out.println("成功添加大区");
                                sendText(referee,"有一个用户通过你的二维码关注平台，用户名："+areas.getName());
                            }
                        }else if(flag == 1){
                            String oi = t.replaceFirst("1","");
                            System.out.println(oi);
                            Roles roles = new Roles();
                            roles.setOpenId(openId);
                            roles.setName(json.get("nickname").getAsString());
                            if(map.get("Ticket")!=null){
                                String referee = openId;
                                System.out.println("成功添加角色");
//                                sendText(referee,"有一个用户通过你的二维码关注平台，用户名："+areas.getName());
                            }
                        }else if(flag == 2){
                            User user = new User();
                            user.setOpenId(openId);
                            user.setUsername(json.get("nickname").getAsString());
                            if(map.get("Ticket")!=null){
                                String referee = openId;
                                System.out.println("成功添加大区");
//                                sendText(referee,"有一个用户通过你的二维码关注平台，用户名："+areas.getName());
                            }
                        }


                    break;
                }
        }
//        return "redirect:/listGood";
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
                    System.out.println("失败！");
                    System.out.println(json1.get("errcode").toString());
                }
                else
                {
                    System.out.println("成功");
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
}
