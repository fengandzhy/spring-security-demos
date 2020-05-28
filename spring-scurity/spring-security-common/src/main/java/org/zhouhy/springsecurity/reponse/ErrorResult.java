package org.zhouhy.springsecurity.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zhouhy.springsecurity.enums.ResultCode;

@Builder //@Builder声明实体，表示可以进行Builder方式初始化
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {

    private Integer status;

    private String message;

    private String exception;

    public static ErrorResult fail(ResultCode resultCode, Throwable e, String message) {
        ErrorResult result = ErrorResult.fail(resultCode, e);
        result.setMessage(message);
        return result;
    }

    /**
     * 对异常枚举进行封装
     */
    public static ErrorResult fail(ResultCode resultCode, Throwable e) {
        ErrorResult result = new ErrorResult();
        result.setMessage(resultCode.desc());
        result.setStatus(resultCode.code());
        result.setException(e.getClass().getName());
        //result.setErrors(Throwables.getStackTraceAsString(e));
        return result;
    }

}
