package cn.cleir.home.until;

import cn.cleir.home.domain.Result;

public class ResultUtil {

    /** 成功返回 */
    public static Result success(Object obj){
        Result res = new Result();
        res.setCode(0);
        res.setMsg("success");
        res.setData(obj);
        return res;
    }

    /** 失败返回 */
    public static Result fail(Integer code, String msg){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

}
