package cn.cleir.home.controller;


import cn.cleir.home.domain.Bus;
import cn.cleir.home.domain.Car;
import cn.cleir.home.domain.Express;
import cn.cleir.home.domain.Result;
import cn.cleir.home.repository.BusRepository;
import cn.cleir.home.repository.CarRespository;
import cn.cleir.home.repository.ExpressRepository;
import cn.cleir.home.service.HomeService;
import cn.cleir.home.until.HttpUtils;
import cn.cleir.home.until.ResultUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/homes")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 公交地铁查询
     */
    @PostMapping("/goto")
    public Result getSubway(@Valid String city, @Valid String address){
        return homeService.getSubway(city, address);
    }

    /**
     * 驾驶分查询
     */
    @PostMapping("/car")
    public Result getCarScore(@Valid String driveNum, @Valid String fileNum){
        return homeService.getCarScore(driveNum,fileNum);
    }

    /**
     * 快递查询
     */
    @PostMapping("/exp")
    public Express getExp(@Valid String no, @Valid String type){
        return homeService.getExp(no,type);
    }


}
