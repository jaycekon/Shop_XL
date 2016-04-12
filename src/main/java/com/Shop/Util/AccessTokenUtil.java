package com.Shop.Util;

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

    // 获取access_token值
    private static String getAccess_Token(String aurl)
    {
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
            System.out.println(json);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String access_token = json.get("access_token").getAsString();
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
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        String accessToken = getAccess_Token(url);
       return accessToken;
    }

    public static String getQrCodePic() {
        HttpClient client = new DefaultHttpClient();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + getAccessToken();

        HttpPost post = new HttpPost(url);
        JsonParser jsonParser = new JsonParser();
        post.addHeader(HTTP.CONTENT_TYPE, "application/json");
        long flag = System.currentTimeMillis();
        String encoderJson = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 0"+flag+"}}}";
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

}
