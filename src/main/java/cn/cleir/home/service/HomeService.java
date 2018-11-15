package cn.cleir.home.service;

import cn.cleir.home.domain.Bus;
import cn.cleir.home.domain.Car;
import cn.cleir.home.domain.Express;
import cn.cleir.home.domain.Result;
import cn.cleir.home.repository.BusRepository;
import cn.cleir.home.repository.CarRespository;
import cn.cleir.home.repository.ExpressRepository;
import cn.cleir.home.until.APIResultUntil;
import cn.cleir.home.until.HttpUtils;
import cn.cleir.home.until.ResultUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.cleir.home.config.configCode.*;

@Service
public class HomeService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private CarRespository carRespository;
    @Autowired
    private ExpressRepository expressRepository;

    /**
     * 公交地铁查询
     */
    public Result getSubway(String city,String address){
        String host = "https://jisugjdt.market.alicloudapi.com";
        String path = "/transit/nearby";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("address", address);
        querys.put("city", city);

        //插入数据库
        Bus bus = new Bus();
        bus.setAddress(address);
        bus.setCity(city);
        bus.setDate(new Date());

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            String result = EntityUtils.toString(response.getEntity());
            bus.setResult(result);
            busRepository.save(bus);
            //解析返回结果
            JSONObject busJson = JSON.parseObject(result);
            if(busJson.getInteger("status") == 0){
                return ResultUtil.success(busJson.get("result"));
            }else{
                return ResultUtil.fail(busJson.getInteger("status"), busJson.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail(-1,"服务器未知错误");
        }
    }

    /**
     * 驾驶分查询
     */
    public Result getCarScore(String driveNum,String fileNum){
        String host = "https://jisujszkf.market.alicloudapi.com";
        String path = "/driverlicense/query";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("licenseid", fileNum);
        querys.put("licensenumber", driveNum);

        Car car = new Car();
        car.setDriveNum(driveNum);
        car.setFileNum(fileNum);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            car.setResult(result);
            //解析返回结果
            JSONObject carJson = JSON.parseObject(result);
            //解析返回结果中的result
            JSONObject resultJson = JSONObject.parseObject(carJson.getString("result"));
            if(carJson.getInteger("status") == 0){
                try{
                    //获取扣分
                    car.setScore(resultJson.getInteger("score"));
                } catch (NullPointerException e){
                    return ResultUtil.fail(-1,"无返回结果");
                }
                return ResultUtil.success(carJson.get("result"));
            }else{
                return ResultUtil.fail(carJson.getInteger("status"), carJson.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail(-1,"服务器未知错误发生");
        }
    }

    /**
     * 快递查询
     */
    public Express getExp(String no,String type){
        String host = "https://cexpress.market.alicloudapi.com";
        String path = "/cexpress";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", no);
        querys.put("type", type);

        Express exp = new Express();
        exp.setNo(no);
        exp.setType(type);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            JSONObject expJson = JSONObject.parseObject(result);
            exp.setList(expJson.getString("list"));
            exp.setName(expJson.getString("name"));
            return expressRepository.save(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Result getSub(String city,String address){
        String host = "https://jisugjdt.market.alicloudapi.com";
        String path = "/transit/nearby";
        String method = "GET";
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("address", address);
        querys.put("city", city);
        JSONObject resultJson = APIResultUntil.apiSend(host,path,method,querys);
        if(resultJson==null){
            return ResultUtil.fail(-1,"服务器未知错误发生");
        }
        else if(resultJson.getInteger("status") == 0){
            return ResultUtil.success(resultJson.get("result"));
        }else{
            return ResultUtil.fail(resultJson.getInteger("status"), resultJson.getString("msg"));
        }
    }

    public Result getWeChatHot(String resultCode,String resultMsg){
        String host = "http://ali-weixin-hot.showapi.com";
        String path = "/articleDetalList";
        String method = "GET";
        Map<String, String> querys = new HashMap<String, String>();
        JSONObject resultJson = APIResultUntil.apiSend(host,path,method,querys);
        if(resultJson==null){
            return ResultUtil.fail(-1,"服务器未知错误发生");
        }
        else if(resultJson.getInteger(resultCode) == 0){
            return ResultUtil.success(resultJson.get(resultMsg));
        }else{
            return ResultUtil.fail(resultJson.getInteger(resultCode), resultJson.getString(resultMsg));
        }
    }

}
