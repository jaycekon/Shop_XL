package com.Shop.Controller;

import com.Shop.Model.*;
import com.Shop.Service.OrdersService;
import com.Shop.Service.TerraceService;
import com.Shop.Service.UserService;
import com.Shop.Util.*;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Administrator on 2016/4/14.
 */
@Controller
public class WebChatController {
    @Autowired
    private TerraceService terraceService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;

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
                                if (userService.addArea(areas)) {
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

    public String sign(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.info("验证服务器");
        //验证服务器地址
		String signature=request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		PrintWriter out = response.getWriter();

		if(WebChatUtil.checkSignature(signature,timestamp,nonce)){
            log.info("check success");
			out.write(echostr);
			out.flush();
			out.close();
		}
        return null;
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


    @RequestMapping(value="refund/{id}")
    public String refund(HttpSession session,@PathVariable("id") int order_id) throws Exception{
        //统计要付的钱
        WithdrawalsOrder withdrawalsOrder  = ordersService.findWithdrawalsOrderById(order_id);
        int money = (int)(withdrawalsOrder.getPrices()*100);
        String openId;
        if(withdrawalsOrder.getRoles()!=null) {
            openId = withdrawalsOrder.getRoles().getOpenId();
        }else{
            openId = withdrawalsOrder.getAreas().getOpenId();
        }
        String nonce_str = WebChatUtil.generateStr(32);
        String ip = "131.25.0.7";
        int orderId = order_id;

        //组装参数
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("amount", String.valueOf(money));
        map.put("check_name", "NO_CHECK");
        map.put("desc", "拿去花");
        map.put("mch_appid", WebChatUtil.getAPPID());
        map.put("mchid", WebChatUtil.getTenantId());
        map.put("nonce_str", nonce_str);
        map.put("openid", openId);
        map.put("partner_trade_no", withdrawalsOrder.getUuid());
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
                if(resultMap.get("return_code").equals("FAIL")){
                }else{
                    withdrawalsOrder.setStatus(1);
                    withdrawalsOrder.setCommitDate(new Date());
                    ordersService.updateWithdrawalsOrder(withdrawalsOrder);
                    log.info(resultMap.get("prepay_id"));
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            if(withdrawalsOrder.getRoles()!=null) {
                return "redirect:/listRoleCommission";
            }else{
                return "redirect:/listAreaCommission";
            }
        }
    }

    /**
     * 用户退款
     * @return
     * @throws Exception
     */
    @RequestMapping(value="exitToUser/{id}")
    public String exitOrders(@PathVariable("id") int id) throws Exception{
        //统计要付的钱
        OrderProduct orderProduct = ordersService.findOrderProductById(id);
        Orders orders = orderProduct.getOrders();
        int money = (int)(orderProduct.getPrices()*orderProduct.getCount()*100);
        log.info(money);
        money=100;
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
                    log.info("退款失败");
                }else{
                    log.info("退款成功!");
                    orderProduct.setStauts(2);
                    orders.setStatus(0);
                    Profit profit = terraceService.findProfit();
                    float pv = orderProduct.getPv() * orderProduct.getCount();
                    float prices  = orderProduct.getPrices() * orderProduct.getCount();
                    float totalPv = orders.getTotalPV();
                    float total = orders.getTotalProfit();
                    total = total - (pv -prices);
                    int count = orders.getNumber() - orderProduct.getCount();
                    orders.setNumber(count);
                    log.info("总的PV值:"+totalPv+" 商品PV值："+pv);
                    totalPv = totalPv - pv;
                    log.info("总的PV值："+totalPv);
                    log.info("总的货款："+total);
                    orders.setTotalPV(totalPv);   //退款后设置总的pv
                    orders.setTotalProfit(total);        //设置货款
                    if(orders.getRoles()!=null) {

                        //更新角色中的佣金
                        Roles roles = orders.getRoles();
                        float totalCommission = roles.getTotalCommission();
                        float waitCommission = roles.getWaitCommission();
                        log.info("角色总佣金："+totalCommission+" 角色待结算佣金："+waitCommission);
                        totalCommission = totalCommission - (pv * profit.getRole_count())/100;
                        waitCommission = waitCommission - (pv * profit.getRole_count())/100;
                        log.info("角色总佣金："+totalCommission+" 角色待结算佣金："+waitCommission);
                        roles.setWaitCommission(waitCommission);
                        roles.setTotalCommission(totalCommission);
                        userService.updateRoles(roles);

                        //更新大区中的佣金
                        Areas areas = orders.getAreas();
                        totalCommission = areas.getTotalCommission();
                        waitCommission = areas.getWaitCommission();
                        log.info("大区总佣金："+totalCommission+" 大区待结算佣金："+waitCommission);
                        totalCommission = totalCommission - (pv * profit.getArea_count())/100;
                        waitCommission = waitCommission - (pv * profit.getArea_count())/100;
                        log.info("大区总佣金："+totalCommission+" 大区待结算佣金："+waitCommission);
                        areas.setTotalCommission(totalCommission);
                        areas.setWaitCommission(waitCommission);
                        userService.updateAreas(areas);

                    }
                    ordersService.updateOrderProduct(orderProduct);
                    List<OrderProduct> orderProducts = userService.findOrderProductByOrderId(orders.getId());
                    int flag=0;
                    for(OrderProduct o:orderProducts){
                        log.info("订单项退款状态："+o.getStauts());
                        if(o.getStauts()==0){
                            flag = 1;
                        }
                    }
                    log.info("标识符："+flag);
                    if(flag==0){
                        orders.setD(2);
                    }
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


}
