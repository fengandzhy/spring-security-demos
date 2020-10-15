package org.zhouhy.springsecurity.exception;

import lombok.Getter;
import org.zhouhy.springsecurity.enums.ResultStatus;

@Getter
public class ResultException extends Exception{

    ResultStatus resultStatus;

    public ResultException(){
        this(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    public ResultException(ResultStatus status){
        super(status.getMessage());
        this.resultStatus = status;
    }
}
