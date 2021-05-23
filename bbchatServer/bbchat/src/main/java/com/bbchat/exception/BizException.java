package com.bbchat.exception;

import com.bbchat.common.ConstantUtil;

public class BizException extends RuntimeException{
    private static final long serialVersionUID = -3637752166318319275L;

    private ConstantUtil.BizExceptionCause cause;
    public BizException(ConstantUtil.BizExceptionCause cause){
        this.cause=cause;
    }
    public BizException(String msg){
        super(msg);
    }

    public Integer getCauseCode(){
        return this.cause.code;
    }
    public  String getCauseReason(){
        return this.cause.reason;
    }
}
