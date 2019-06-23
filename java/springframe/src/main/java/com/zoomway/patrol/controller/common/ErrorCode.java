package com.zoomway.patrol.controller.common;

public enum ErrorCode {
    SUCCESS(200,"成功"),
    PARAMS_ERROR(400,"参数错误"),
    NO_PERMISSION(401,"权限不足"),
    JSON_PARSE_ERROR(402,"Json解析错误"),
    SERVER_ERROR(500,"服务器异常"),
    UNKNOW_ERROR(505,"未知错误");


    private int code;
    private String msg;

    ErrorCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
