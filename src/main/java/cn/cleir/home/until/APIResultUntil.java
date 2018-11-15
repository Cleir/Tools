package cn.cleir.home.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import static cn.cleir.home.config.configCode.APPCODE;

public class APIResultUntil {

    public static JSONObject apiSend(String host, String path, String method, Map<String, String> querys){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse response = "GET".equals(method) ? HttpUtils.doGet(host, path, method, headers, querys) :
                    HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            JSONObject resultJson = JSON.parseObject(result);
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
