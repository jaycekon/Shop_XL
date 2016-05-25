package com.Shop.Util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class UserInfoUtil {
    public static JsonObject getUserInfo(String openId){
        String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+
                WebChatUtil.getAccessToken()+"&openid="+openId+"&lang=zh_CN";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        JsonObject json =null;
        try{
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            json = jsonparer.parse(responseContent)
                    .getAsJsonObject();
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                    System.out.println("获取用户信息失败："+json.get("errcode").toString());
                    return null;
                }else{// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    System.out.println(""+json);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return json;
        }
    }
}
