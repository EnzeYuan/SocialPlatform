package com.westonline.socialplatform.pojo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class Result {
    private int code;
    private String msg;
    private List<?> data;
    private Map<ReviewTerm, ArrayList<ReviewTerm>> map;

    public Map<ReviewTerm, ArrayList<ReviewTerm>> getMap() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result result = (Result) o;
        return code == result.code && Objects.equals(msg, result.msg) && Objects.equals(data, result.data) && Objects.equals(map, result.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data, map);
    }

    public Result(int code, String msg, Map<ReviewTerm, ArrayList<ReviewTerm>> map) {
        this.code = code;
        this.msg = msg;
        this.map = map;
    }

    public void setMap(Map<ReviewTerm, ArrayList<ReviewTerm>> map) {
        this.map = map;
    }

    public Result(int code, String msg, List<Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
        this.code = 200;
        this.msg = "success";
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }


    //系统异常
    public static Result error() {
        Result result = new Result();
        result.setCode(500);
        result.setMsg("error");
        return result;
    }

    public Result codeAndMessage(Integer code, String message) {
        this.setCode(code);
        this.setMsg(message);
        return this;
    }
}
