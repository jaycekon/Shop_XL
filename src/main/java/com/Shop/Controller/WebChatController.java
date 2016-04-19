package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.Shop.Util.*;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/14.
 */
@Controller
public class WebChatController {
    @Autowired
    private TerraceService terraceService;
    @Autowired
    private UserService userService;

    private final static String wechatName ="xiaoguozhushou";
    Logger log = Logger.getLogger(WebChatController.class);

    /**
     * 接收微信传送过来的信息
     * @param request
     * @param response
     * @throws IOException
     */
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
//		if(WebChatUtil.checkSignature(signature,timestamp,nonce)){
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
                        tm.setContent(WebChatUtil.getQrCodePic(9,flag));
                        break;
                    case "生成角色二维码":
                        tm.setFromUserName(wechatName);
                        tm.setToUserName((String)map.get("FromUserName"));
                        tm.setCreateTime(String.valueOf(new Date().getTime()));
                        tm.setMsgType("text");
                        Areas areas = terraceService.findAreasByOpenId(openId);
                        if(areas !=null){
                            tm.setContent(WebChatUtil.getQrCodePic(1,areas.getId()));
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
                            tm.setContent(WebChatUtil.getQrCodePic(2,roles.getId()));
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
                        JsonObject json = UserInfoUtil.getUserInfo(openId);
                        log.info("用户关注公众号123!");
                        if(!terraceService.checkOpenId(openId)){
                            WebChatUtil.sendText(openId,"用户已存在数据库中："+json.get("nickname").getAsString());
                        }
                        if(map.get("Ticket") ==null){
                            log.info("用户通过公众号关注");
                            String referee = openId;
                            User user = new User();
                            user.setOpenId(openId);
                            user.setUsername(json.get("nickname").getAsString());
                            user.setImg(json.get("headimgurl").getAsString());
                            userService.addUser(user);
                            WebChatUtil.sendText(openId,"恭喜你成为用户："+user.getUsername());
                        }else {
                            log.info("用户关注，扫码关注！！！");
                            String t =map.get("EventKey").toString().replace("qrscene_","");
                            String flags = t.substring(0,1);
                            Long oi = Long.parseLong(t);
                            int flag = Integer.valueOf(flags);
                            log.info("123456");
                            if (flag == 9) {
                                flags = t.replaceFirst("9", "");
                                Areas areas = new Areas();
                                areas.setOpenId(openId);
                                String nickname = new String(json.get("nickname").getAsString());
                                areas.setName(nickname);
                                areas.setImg(json.get("headimgurl").getAsString());
                                areas.setDate(new Date());
                                areas.setFlag(Long.parseLong(flags));
                                Areas a = terraceService.findAreasByFlag(oi);
                                if (a != null) {
                                    WebChatUtil.sendText(openId, "该二维码已失效，你已成为普通用户：" + areas.getName());
                                    break;
                                }
                                if (!userService.addArea(areas)) {
                                    WebChatUtil.sendText(openId, "用户已成为大区，用户名：" + areas.getName());
                                    break;
                                }
                                if (map.get("Ticket") != null) {
                                    WebChatUtil.sendText(openId, "有一个用户通过你的二维码关注平台，用户名：" + areas.getName());
                                }
                            } else if (flag == 1) {
                                flags = t.replaceFirst("1", "");
                                Areas areas = terraceService.findAreasById(Integer.parseInt(flags));
                                Roles roles = new Roles();
                                roles.setOpenId(openId);
                                roles.setName(json.get("nickname").getAsString());
                                roles.setImg(json.get("headimgurl").getAsString());
                                roles.setDate(new Date());
                                roles.setAreas(areas);
                                Roles role = terraceService.findRolesByOpenId(openId);
                                if (role != null) {
                                    WebChatUtil.sendText(areas.getOpenId(), "角色通过扫面您的二维码已成为用户，角色名：" + roles.getName());
                                    WebChatUtil.sendText(openId, "恭喜你称为角色，您的大区：" + areas.getName());
                                    break;
                                } else {
                                    WebChatUtil.sendText(areas.getOpenId(), "角色通过扫面您的二维码已成为用户，角色名：" + roles.getName());
                                    WebChatUtil.sendText(openId, "恭喜你称为角色，您的大区：" + areas.getName());
                                    userService.addRoles(roles);
                                }
                                if (map.get("Ticket") != null) {
                                    WebChatUtil.sendText(openId, "有一个角色通过你的二维码关注平台，用户名：" + roles.getName());
                                }
                            } else if (flag == 2) {
                                flags = t.replaceFirst("2", "");
                                Roles roles = terraceService.findROlesById(Integer.parseInt(flags));
                                User user = new User();
                                user.setOpenId(openId);
                                user.setUsername(json.get("nickname").getAsString());
                                user.setRoles(roles);
                                user.setImg(json.get("headimgurl").getAsString());
                                userService.addUser(user);
                                if (map.get("Ticket") != null) {
                                    String referee = openId;
                                    WebChatUtil.sendText(roles.getOpenId(), "用户通过扫面您的二维码已成为用户，用户名：" + user.getUsername());
                                    WebChatUtil.sendText(openId, "恭喜你称为用户，您的销售员：" + roles.getName());
                                }
                            }else{
                                log.info("添加用户！");
                                log.info("用户关注公众号 else！！");
                                String referee = openId;
                                User user = new User();
                                user.setOpenId(openId);
                                user.setUsername(json.get("nickname").getAsString());
                                user.setImg(json.get("headimgurl").getAsString());
                                userService.addUser(user);
                                WebChatUtil.sendText(openId,"恭喜你称为用户："+user.getUsername());
                            }
                        }

                        break;
                }
        }
    }


    //模拟支付
    @RequestMapping("/weixin/preparePayOrder/{id}")
    public String addOrder(HttpServletRequest request, Model model, @PathVariable(value="id")int id) throws Exception{
        Orders orders = userService.findOrdersById(id);
        String notify_url ="http://weijiehuang.productshow.cn/paySuccess";
        String prepayId=WebChatUtil.placeOrdersJSAPI(id,orders.getPrices(),request,orders.getUuid(),notify_url);
        Map<String,Object> payMap = WebChatUtil.generatePaySign(prepayId);
        payMap.put("payId", id);
        model.addAttribute("payMap",payMap);
        return "Pay/pay";
    }

    //模拟支付
    @RequestMapping("/weixin/preparePayCountOrder/{id}")
    public String addCountOrder(HttpServletRequest request, Model model, @PathVariable(value="id")int id) throws Exception{
        CountOrder countOrder = userService.findCountOrderById(id);
        String notify_url ="http://weijiehuang.productshow.cn/personSignSuccess";
        String prepayId=WebChatUtil.placeOrdersJSAPI(id,countOrder.getPrices(),request,countOrder.getUuid(),notify_url);
        Map<String,Object> payMap = WebChatUtil.generatePaySign(prepayId);
        payMap.put("payId", id);
        model.addAttribute("payMap",payMap);
        log.info("success");
        return "Pay/payCountOrder";
    }




    @RequestMapping("/weixin/signature")
    public @ResponseBody Map<String,Object> getSignature(@RequestParam("url")String url, HttpSession session
            , HttpServletRequest request) throws Exception{
        HashMap<String,Object> sMap = new HashMap<String,Object>();
        sMap.put("appId", WebChatUtil.getAPPID());
        sMap.put("timeStamp", System.currentTimeMillis()/1000);
        sMap.put("nonceStr", WebChatUtil.generateStr(32));

        String jsapi = WebChatUtil.getJsapi(request);

        log.info("获取jsapi:"+jsapi);

        String stringB = "jsapi_ticket="+jsapi+"&noncestr="+sMap.get("nonceStr")
                +"&timestamp="+sMap.get("timeStamp")+"&url="+url;

        String signature = SHA1.encode(stringB);
        sMap.put("signature", signature);
        return sMap;
    }

}
