package com.Shop.Util;

import com.Shop.Controller.AdminController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class AccessTokenUtil {
    private static String  APPID = "wx05208e667b03b794";
    private static String  APPSECRET = "d6ee7f14c7481fee93cbd4f569564dd7";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    static Logger  log  =Logger.getLogger(AccessTokenUtil.class);
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
            log.info("获取Access_token失败!");
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }

    public static String getAccessToken(){
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        String accessToken = getAccess_Token(url);
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
        String encoderJson = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+type+flag+"}}}";
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
        Logger logger = Logger.getLogger(AccessTokenUtil.class);
        logger.info("enter checkSignature");
        String[] s = {"weijiehuang",timestamp,nonce};
        Arrays.sort(s);
        String str="";
        for(int i=0;i<s.length;i++){
            str+=s[i];
        }
        str = SHA1.encode(str);
        return str.equals(signature);
    }

    public static void getCode(String code,HttpServletRequest request){
        String rurl="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSECRET+"&code="+code+"&grant_type=authorization_code";
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
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+AccessTokenUtil.getAccessToken();
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
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+AccessTokenUtil.getAccessToken();
        AccessTokenUtil.post(url,strJson);
    }


}
