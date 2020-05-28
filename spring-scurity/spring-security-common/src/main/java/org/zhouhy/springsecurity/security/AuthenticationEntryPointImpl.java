package org.zhouhy.springsecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.zhouhy.springsecurity.enums.ResultCode;
import org.zhouhy.springsecurity.reponse.ErrorResult;
import org.zhouhy.springsecurity.utils.JsonUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info(authException.getMessage());
        log.info(authException.getClass().getSimpleName());
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ResultCode resultCode = ResultCode.getResultCodeByDesc("没有登录");
        ErrorResult errorResult = ErrorResult.fail(resultCode,authException);
        String data = JsonUtil.object2Json(errorResult);
        out.write(data);
        out.flush();
        out.close();
    }
}
