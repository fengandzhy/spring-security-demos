package org.zhouhy.springsecurity.enums;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ResultStatus {

    SUCCESS(HttpStatus.OK, 200, "OK"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),;

    private HttpStatus httpStatus;
    private Integer code;
    private String message;

    ResultStatus(HttpStatus httpStatus, Integer code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
