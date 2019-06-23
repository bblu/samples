package com.zoomway.patrol.controller.common;

import java.io.Serializable;
import java.util.HashMap;

public class HttpResult extends HashMap<String,Object> {
    //private int code = 200;
    //private String message;
    //private Object data;

    /**
     * 默认构造器
     */
    public HttpResult() {
        this.setCode(200);
        this.setMessage("success");
    }

    /**
     * @param code 是否成功
     */
    public HttpResult(int code) {
        this.setCode(code);
    }

    /**
     * @param code    error code
     * @param message success or error messages
     */
    public HttpResult(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     * @param message 消息
     * @param data    数据
     */
    public HttpResult(int code, String message, Object data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public void setCode(int code) {
        this.put("code",code);
    }


    public HttpResult setMessage(String message) {
        this.put("message", message);
        return this;
    }

    public HttpResult setData(Object data) {
        this.put("data", data);
        return this;
    }
}