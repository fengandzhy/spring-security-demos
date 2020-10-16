package org.zhouhy.springsecurity.result;

import lombok.Getter;
import lombok.ToString;
import org.zhouhy.springsecurity.enums.ResultStatus;

import java.io.Serializable;

@Getter
@ToString
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public Result(ResultStatus status, T data){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public static Result<Void> success(){
        return new Result<Void>(ResultStatus.SUCCESS,null);
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(ResultStatus.SUCCESS,data);
    }

    public static <T> Result<T> success(ResultStatus resultStatus,T data){
        if(resultStatus == null){
            return success(data);
        }
        return new Result<T>(resultStatus,data);
    }

    public static <T> Result<T> fail(){
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR,null);
    }

    public static <T> Result<T> fail(ResultStatus status){
        return fail(status,null);
    }

    public static <T> Result<T> fail(ResultStatus status,T data){
        if(status == null){
            new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR,null);
        }
        return new Result<T>(status,data);
    }
}
