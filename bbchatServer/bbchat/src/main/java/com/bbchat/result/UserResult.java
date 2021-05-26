package com.bbchat.result;

/**
 * @author thisisbadBao
 * @Date 2021--25-3:15 PM
 */
public class UserResult {
    //相应码
    private int code;

    //用户名
    private String username;

    public UserResult(int code, String username) {
        this.code = code;
        this.username = username;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
