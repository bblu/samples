package cn.bblu.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpResult extends ResponseEntity<Map> {

    public static HttpResult ParamError(String msg){
        Map<String,String> error = new HashMap<>();
        error.put("error",msg);
        return new HttpResult(error, HttpStatus.BAD_REQUEST);
    }
    public static HttpResult ParamError(Map msgMap){
        return new HttpResult(msgMap, HttpStatus.BAD_REQUEST);
    }

    public static HttpResult ServerError(Object obj){
        Map<String,Object> error = new HashMap<>();
        error.put("error", obj);
        return new HttpResult(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static HttpResult success(Object obj){
        Map<String,Object> body = new HashMap<>();
        body.put("data", obj);
        return new HttpResult(body, HttpStatus.OK);
    }

    public static HttpResult message(String msg){
        Map<String,String> body = new HashMap<>();
        body.put("message", msg);
        return new HttpResult(body, HttpStatus.OK);
    }

    public HttpResult(){
        super(HttpStatus.OK);
    }

    public HttpResult(HttpStatus status){
        super(status);
    }

    public HttpResult(Map body, HttpStatus status){
        super(body,status);
    }
}

/*
public class HttpResult extends HashMap<String,Object> {
    //private int code = 200;
    //private String message;
    //private Object data;

    public HttpResult() {
        this.setCode(200);
        this.setMessage("success");
    }

    public HttpResult(int code) {
        this.setCode(code);
    }


    public HttpResult(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

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
*/