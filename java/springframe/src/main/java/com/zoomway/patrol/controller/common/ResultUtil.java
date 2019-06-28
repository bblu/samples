package com.zoomway.patrol.controller.common;

/**
 * 公共响应结果成功失败的静态方法调用
 *
 */
public class ResultUtil {
    /**
     * return success
     *
     * @param data
     * @return
     */
    public static HttpResult success(Object data) {
        HttpResult result = null;// new HttpResult(200,"success",data);
        return result;
    }

    /**
     * return error
     *
     * @param code error code
     * @param msg  error message
     * @return
     */
    public static HttpResult failed(int code, String msg) {
        HttpResult result = null;// new HttpResult(code,msg);
        return result;
    }


}
