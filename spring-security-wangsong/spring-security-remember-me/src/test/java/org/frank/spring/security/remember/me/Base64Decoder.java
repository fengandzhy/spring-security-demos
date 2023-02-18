package org.frank.spring.security.remember.me;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Decoder {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    void contextLoads() throws UnsupportedEncodingException {
        String base64Str = "amF2YWJveToxNTg5MTA0MDU1MzczOjI1NzhmZmJjMjY0ODVjNTM0YTJlZjkyOWFjMmVmYzQ3";
        String s = new String(Base64.getDecoder().decode(base64Str), "UTF-8");
        logger.info(s);
    }
}