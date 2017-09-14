package com.yydscm.Util;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.view.Result;

/**
 * Created by chenzhaopeng on 2017/6/8.
 */
public class ResultUtil {

    /**
     * 成功后调用
     *
     * @param object 数据模型
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), object, true);
        return result;
    }

    public static Result success(Object object, String token) {
        Result result = new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), object, true);
        result.setToken(token);
        return result;
    }

    /**
     * 数据为空,成功后调用
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 失败后调用
     *
     * @param code 状态码
     * @param msg  提示消息
     * @return
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result(code, msg, false);
        return result;
    }

    public static Result error(String code,String msg){
        Result result=new Result(code,msg,false);
        return result;
    }
}
