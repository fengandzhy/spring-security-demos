package org.zhouhy.springsecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }
}
