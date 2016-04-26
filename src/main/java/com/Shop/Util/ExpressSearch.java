package com.Shop.Util;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExpressSearch {
	
	public static String consult1(String type, String postId) {
		String url = "http://www.kuaidi100.com/query?type=" + type + "&postid="
				+ postId;
		System.out.println("---------------?"+url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		JsonParser jsonparer = new JsonParser();//  初始化解析json格式的对象
		String result = "error";
		try {
			HttpResponse res = client.execute(get);
			String responseContent = null; //  响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
			//  将json字符串转换为json对象
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("status").getAsString().equals("200")) {
					System.out.println("success!!!!!!!!!!!!!!!!!!!!");
 					System.out.println(json.toString());
					result = json.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//  关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
			return result;
		}

	}
	public static String consult3(String type, String postId){
		String url="http://www.kuaidi100.com/query?type="+type+"&postid="+postId+"&show=3";

		HttpClient client = new DefaultHttpClient();
		        HttpGet get = new HttpGet(url);
		        get.setHeader("Accept","text双击查看原图ml,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
		        get.setHeader("Accept-Language","zh-cn,zh;q=0.5");  
		        get.setHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");  
		        get.setHeader("Referer", "http://www.kuaidi100.com/qurey");  
		        get.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
		        
		        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		        String result = "error";
		        try
		        {
		            HttpResponse res = client.execute(get);
		            String responseContent = null; // 响应内容
		            HttpEntity entity = res.getEntity();
		            responseContent = EntityUtils.toString(entity, "UTF-8");
		            System.out.println(responseContent);
		            JsonObject json = jsonparer.parse(responseContent)
		                    .getAsJsonObject();
		            // 将json字符串转换为json对象
		            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		            {
		                if (json.get("status").getAsString().equals("200"))
		                {	
		                	System.out.println("success!!!!!!!!!!!!!!!!!!!!");
		                	System.out.println(json.toString());
		                	result = json.toString();
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
	public static String consult(String type, String postId){
		String url="http://www.kuaidi100.com/query?type="+type+"&postid="+postId;

		HttpClient client = new DefaultHttpClient();
		        HttpGet get = new HttpGet(url);
		        get.setHeader("Accept","text双击查看原图ml,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
		        get.setHeader("Accept-Language","zh-cn,zh;q=0.5");  
		        get.setHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");  
		        get.setHeader("Referer", "http://www.kuaidi100.com/qurey");  
		        get.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
		        
		        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		        String result = "error";
		        try
		        {
		            HttpResponse res = client.execute(get);
		            String responseContent = null; // 响应内容
		            HttpEntity entity = res.getEntity();
		            responseContent = EntityUtils.toString(entity, "UTF-8");
		            System.out.println(responseContent);
		            JsonObject json = jsonparer.parse(responseContent)
		                    .getAsJsonObject();
		            // 将json字符串转换为json对象
		            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		            {
		                if (json.get("status").getAsString().equals("200"))
		                {	
		                	System.out.println("success!!!!!!!!!!!!!!!!!!!!");
		                	System.out.println(json.toString());
		                	result = json.toString();
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
}
