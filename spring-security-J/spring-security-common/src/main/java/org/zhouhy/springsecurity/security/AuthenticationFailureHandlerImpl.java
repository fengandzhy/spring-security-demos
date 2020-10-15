package org.zhouhy.springsecurity.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.zhouhy.springsecurity.enums.ResultStatus;
import org.zhouhy.springsecurity.result.Result;
import org.zhouhy.springsecurity.utils.JsonUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Result result = Result.fail(ResultStatus.LOGIN_FAIL,null);
        String resultData = JsonUtil.object2Json(result);
        out.write(resultData);
        out.flush();
        out.close();
    }
}
