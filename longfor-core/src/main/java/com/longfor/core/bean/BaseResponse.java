package com.longfor.core.bean;

/**
 * @author: tongzhenhua
 * @date: 2017/12/25
 * @function: 基础返回类型
 */
public class BaseResponse {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
