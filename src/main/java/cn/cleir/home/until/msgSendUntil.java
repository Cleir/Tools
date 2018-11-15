package cn.cleir.home.until;

import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class msgSendUntil {
    public static void sendSms(String mobile, String param) {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        querys.put("city", param);
        APIResultUntil.apiSend(host, path, method, querys);
    }
}
