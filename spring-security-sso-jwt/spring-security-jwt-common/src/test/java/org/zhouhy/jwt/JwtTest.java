package org.zhouhy.jwt;

import org.junit.Test;
import org.zhouhy.jwt.utils.RsaUtils;

public class JwtTest {

    private String privateKey = "d:/tools/auth_key/id_key_rsa";

    private String publicKey = "d:/tools/auth_key/id_key_rsa.pub";

    @Test
    public void test1() throws Exception{
        RsaUtils.generateKey(publicKey,privateKey,"dpb",1024);
    }
}
