package org.zhouhy.springsecurity.enums;


/**
 * 定义枚举USER_NOT_FOUND(20002,"用户名不存在"); 这个一定要放在private Integer code; 之前
 *
 * */
public enum ResultCode {


    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(500,"未知错误"),
    FORBIDDEN(403,"没有权限"),
    NO_LOGIN(20000,"没有登录"),
    AUTHENTICATION_ERROR(20001,"鉴权失败"),
    USER_NOT_FOUND(20002,"用户名不存在");

    private Integer code;
    private String desc;
    ResultCode(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer code(){
        return this.code;
    }

    public String desc(){
        return this.desc;
    }

    public static ResultCode getResultCodeByDesc(String desc){
        for(ResultCode e:ResultCode.values()){
            if (e.desc().equals(desc)){
                return e;
            }
        }
        return ResultCode.UNKNOWN_ERROR;
    }
}
