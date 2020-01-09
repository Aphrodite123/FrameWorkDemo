package com.aphrodite.framework.model.network.response;

/**
 * Created by Aphrodite on 2019/5/20.
 */
public abstract class BaseResponse {
    private int code;
    private String msg;

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
}
