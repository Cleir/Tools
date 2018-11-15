package cn.cleir.home.controller;

import cn.cleir.home.domain.Express;
import cn.cleir.home.domain.Result;
import cn.cleir.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return homeService.getSub(city, address);
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

    @GetMapping("/weChat")
    public Result weChatHot(){
        return homeService.getWeChatHot("showapi_res_code","showapi_res_body");
    }


}
