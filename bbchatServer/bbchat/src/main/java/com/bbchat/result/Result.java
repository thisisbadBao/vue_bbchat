package com.bbchat.result;

/**
 * @author thisisbadBao
 * @Date 2021--23-8:24 PM
 */
public class Result {
    //响应码
    private int code;

    public Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
