package com.Shop.Util;

import com.Shop.Controller.AdminController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class WebChatUtil {
    private static String  APPID = "wx05208e667b03b794";
    private static String  APPSECRET = "d6ee7f14c7481fee93cbd4f569564dd7";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String TENANT_ID = "1272412901";
    private static final String KEY = "huanlingkejihuangbianfenbu2016ph";

    static Logger  log  =Logger.getLogger(WebChatUtil.class);
    // 获取access_token值
    private static String getAccess_Token(String aurl)
    {

        log.info("获取Access_token");
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(aurl);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        String result = "error";
        try
        {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            JsonObject json = jsonparer.parse(responseContent)
                    .getAsJsonObject();
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String access_token = json.get("access_token").getAsString();
                log.info("成功获取Access_token");
                result = access_token;
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }

    public static String getAccessToken(){
        String url = ACCESS_TOKEN_URL.replace("APPID", getAPPID()).replace("APPSECRET", getAPPSECRET());
        String accessToken = getAccess_Token(url);
        log.info(accessToken);
       return accessToken;
    }




    public static String getQrCodePic(int type,long flag) {
        HttpClient client = new DefaultHttpClient();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + getAccessToken();
        StringBuffer flags = new StringBuffer();
        flags.append(type);
        flags.append(flag);
        System.out.println(flags.toString());
        HttpPost post = new HttpPost(url);
        JsonParser jsonParser = new JsonParser();
        post.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String encoderJson = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "
                +type+flag+"}}}";
        System.out.println(encoderJson);
        StringEntity se = null;
        StringBuffer sb = new StringBuffer();
        sb.append("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=");
        try {
            se = new StringEntity(encoderJson);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);

            HttpResponse res = client.execute(post);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            JsonObject json = jsonParser.parse(responseContent)
                    .getAsJsonObject();
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                    System.out.println("错误：" + json.toString());
                } else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    sb.append(json.get("ticket").getAsString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return sb.toString();
        }
    }

    public static boolean checkSignature(String signature,String timestamp,String nonce){
        Logger logger = Logger.getLogger(WebChatUtil.class);
        logger.info("enter checkSignature");
        String[] s = {"baichengwandian",timestamp,nonce};
        Arrays.sort(s);
        String str="";
        for(int i=0;i<s.length;i++){
            str+=s[i];
        }
        str = SHA1.encode(str);
        return str.equals(signature);
    }

    public static void getCode(String code,HttpServletRequest request){
        String rurl="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ getAPPID() +"&secret="+ getAPPSECRET() +"&code="+code+"&grant_type=authorization_code";
        getReToken(rurl,request);
    }


    // 获取refresh_token,openid值
    private static String getReToken(String rurl, HttpServletRequest req)
    {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(rurl);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        String result = "error";
        try
        {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            JsonObject json = jsonparer.parse(responseContent)
                    .getAsJsonObject();
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                if (json.get("errcode") != null)
                {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                    System.out.println("获取refresh_token,openid值失败");
                    System.out.println(json.get("errcode").toString());
                }
                else
                {
                    //result = json.get("refresh_token").getAsString();
                    result = json.get("openid").getAsString();
                    req.getSession().setAttribute("openId",result);
                    //String aurl ="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+APP_ID+"&grant_type=refresh_token&refresh_token="+result;
                    //result=getAccess_Token(aurl,req);
                    return result;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }


    //创建自定义菜单
    public static void initMenu(){
        String jsonStr=" {\"button\":[";
        jsonStr+="{\"type\":\"view\",\"name\":\"商城\",\"url\":\"http://weijiehuang.productshow.cn/\"}]}";
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ WebChatUtil.getAccessToken();
        log.info(url);
        System.out.println("访问url!");
        post(url,jsonStr);
    }

    //删除菜单
    public static void deleteMenu(){
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+getAccessToken();
        log.info("删除自定义菜单");
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = "error";
        try
        {
            HttpResponse res = client.execute(get);
            HttpEntity entity = res.getEntity();
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("成功删除自定义菜单");
                System.out.println("成功");
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            log.info("获取Access_token失败!");
            System.out.println("成功");
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
    }

    //用post的方法像指定url发送json请求
    public static boolean post(String url, String json)
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


    //发送文本消息
    public static void sendText(String toUser,String content){
        String strJson = "{\"touser\" :\""+toUser+"\",";
        strJson += "\"msgtype\":\"text\",";
        strJson += "\"text\":{";
        strJson += "\"content\":\""+content+"\"";
        strJson += "}}";
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+ WebChatUtil.getAccessToken();
        WebChatUtil.post(url,strJson);
    }


    public static String getAPPID() {
        return APPID;
    }

    public static String getAPPSECRET() {
        return APPSECRET;
    }

    //调用统一下单接口,类型为网页支付，包含多张订单
    public static String placeOrdersJSAPI(long payId,float money,HttpServletRequest request,String uuid,String notify_url) throws Exception{
        //统计要付的钱
        float payMoney=1f;
        //付款成功后跳转url

        String nonce_str = generateStr(32);
        String ip = getIpAddr(request);
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime()+2*24*60*60*1000);   //设置订单过期时间为两天
        //格式化日期，作为参数调用下单接口
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(startDate);
        String endTime = new SimpleDateFormat("yyyyMMddHHmmss").format(endDate);
        String uid = uuid.substring(0,16);
        //组装参数
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("appid", APPID);
        map.put("trade_type","JSAPI");
        map.put("body", "1");
        map.put("mch_id", getTenantId());
        map.put("nonce_str", nonce_str);
        map.put("notify_url", notify_url);
        map.put("openid", request.getSession().getAttribute("openId"));
        map.put("out_trade_no", uid);
        map.put("spbill_create_ip", ip);
        map.put("time_expire",endTime);
        map.put("time_start", startTime);
        map.put("total_fee", String.valueOf(((int)(payMoney*100))));


        String stringA=XMLUtil.mapToStr(map);
        String stringSignTemp=stringA+"&key="+ getKEY();
        String sign = MD5.toMD5(stringSignTemp).toUpperCase();

        map.put("sign", sign);
        String xml="";
        xml+=XMLUtil.mapToXml(map);
        System.out.println("stringA"+stringA);
        System.out.println("xml-"+xml);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        DefaultHttpClient client = new DefaultHttpClient();
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
            String responseContent=EntityUtils.toString(entity, "UTF-8");
            Map<String,Object> map1 = XMLUtil.parseXML(responseContent);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                if(map1.get("return_code").equals("FAIL")){
                }else{
                    return (String)map1.get("prepay_id");
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return "";

        //有待补充，调用网页支付需要openId，暂时没有获取
    }

    //生成调用公众号支付所需的参数，放入map中
    public static Map<String,Object> generatePaySign(String prepayId){
        String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String nonce_str = generateStr(32);
        String stringA = "appId="+APPID+"&nonceStr="+nonce_str
                +"&package=prepay_id="+prepayId+"&signType=MD5"+"&timeStamp="+startTime;
        String stringSignTemp = stringA+"&key="+ getKEY();
        String paySign = MD5.toMD5(stringSignTemp).toUpperCase();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("timestamp", startTime);
        map.put("nonceStr", nonce_str);
        map.put("Package", "prepay_id="+prepayId);
        map.put("signType", "MD5");
        map.put("paySign", paySign);
        return map;
    }


    //获取客户端ip
    private static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String generateStr(int num){
        char[] randomArray={'1','2','3','4','5','6','7','8','9','0','A','B','C',
                'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T',
                'U','V','W','X','Y','Z',};
        String result="";
        for(int i=0;i<num;i++){
            result+=randomArray[(int)(Math.random()*randomArray.length)];
        }

        return result;

    }


    //获取调用jssdk凭证-jsapi
    public static String getJsapi(HttpServletRequest request){
        if(CacheMap.getDefault().get("ticket")==null){
            HttpClient client = new DefaultHttpClient();
            String ticket= null;
            try{
                System.out.println("重新获取jsapi_ticket:-->"+ticket);
                String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+getAccessToken()+"&type=jsapi";

                HttpGet get = new HttpGet(url);
                JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象

                HttpResponse res = client.execute(get);
                String responseContent = null; // 响应内容
                HttpEntity entity = res.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
                JsonObject json = jsonparer.parse(responseContent)
                        .getAsJsonObject();
                // 将json字符串转换为json对象
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    if (json.get("errcode").equals("-1")){// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                        System.out.println("error:-->"+json);
                    }
                    else{// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                        ticket = json.get("ticket").getAsString();
                        CacheMap.getDefault().put("ticket", ticket);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                // 关闭连接 ,释放资源
                client.getConnectionManager().shutdown();
                return ticket;
            }
        }else{
            System.out.println("使用缓存的jsapi_ticket");
            return (String)CacheMap.getDefault().get("ticket");
        }
    }

    //导入证书
    public static CloseableHttpClient loadCert() throws Exception{
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File("/var/xiaoguozhushou/apiclient_cert.p12"));
        try {
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, getTenantId().toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, getTenantId().toCharArray()).build();
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,new String[] { "TLSv1" },null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //设置httpclient的SSLSocketFactory
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
    }

    public static String getKEY() {
        return KEY;
    }

    public static String getTenantId() {
        return TENANT_ID;
    }
}
