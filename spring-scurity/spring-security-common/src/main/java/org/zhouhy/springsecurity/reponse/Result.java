package org.zhouhy.springsecurity.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zhouhy.springsecurity.enums.ResultCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {

    private Integer status;

    /**
     * 2.response描述：对本次状态码的描述。
     */
    private String desc;

    /**
     * 3.data数据：本次返回的数据。
     */
    private T data;

    public static Result success(){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败，指定status、desc
     */
    public static Result fail(Integer status, String desc) {
        Result result = new Result();
        result.setStatus(status);
        result.setDesc(desc);
        return result;
    }

    /**
     * 失败，指定ResultCode枚举
     */
    public static Result fail(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    private void setResultCode(ResultCode resultCode){
        this.status = resultCode.code();
        this.desc = resultCode.desc();
    }
}
