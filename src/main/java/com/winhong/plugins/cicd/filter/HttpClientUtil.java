package com.winhong.plugins.cicd.filter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
/**
 * 利用HttpClient进行post请求的工具类
 * @ClassName: HttpClientUtil 
 * @Description: TODO
 * @author Devin <xxx> 
 * @date 2017年2月7日 下午1:43:38 
 *  
 */
public class HttpClientUtil {
    @SuppressWarnings("resource")
    public static String doPost(String url,String jsonstr,String charset){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/json");
            if(jsonstr != null){
                StringEntity se = new StringEntity(jsonstr);
                se.setContentType("text/json");
                se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
                //httpGet.setEntity(se);
            }
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args){ 
        String url = "https://192.168.208.12/uap/v1/systems";
        String jsonStr = null;
        String result = HttpClientUtil.doPost(url, jsonStr, "utf-8");
        System.out.println("result=="+result);
    }
}