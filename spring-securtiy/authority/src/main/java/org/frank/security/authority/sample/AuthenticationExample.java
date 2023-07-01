package org.frank.security.authority.sample;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AuthenticationExample {

    private static AuthenticationManager am = new SampleAuthenticationManager();
    
    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            //模拟输入用户名密码
            System.out.println("Please enter your username:");
            String name = in.readLine();

            System.out.println("Please enter your password:");
            String password = in.readLine();

            try {
                //根据用户名/密码，生成未认证Authentication
                Authentication request = new UsernamePasswordAuthenticationToken(name, password);
                //交给AuthenticationManager 认证
                Authentication result = am.authenticate(request);
                //将已认证的Authentication放入SecurityContext
                SecurityContextHolder.getContext().setAuthentication(result);
                break;
            } catch (AuthenticationException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
        }

        System.out.println("Successfully authenticated. Security context contains: "
                + SecurityContextHolder.getContext().getAuthentication());        
    }
}
