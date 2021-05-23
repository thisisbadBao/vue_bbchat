package com.bbchat.common;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ConstantUtil {

    @AllArgsConstructor
    public enum BizExceptionCause{
        HAVE_REGISTER(1,"该账号已被注册"),
        NO_USER(2,"没有该用户"),
        WRONG_CODE(3,"密码错误"),

        ;
        public final Integer code;
        public final String reason;
    }
}
