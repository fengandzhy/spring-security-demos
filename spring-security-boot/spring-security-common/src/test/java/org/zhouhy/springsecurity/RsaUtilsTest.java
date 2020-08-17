package org.zhouhy.springsecurity;

import org.junit.Test;
import org.zhouhy.springsecurity.utils.RsaUtils;

public class RsaUtilsTest {
    private String publicFile = "D:\\auth_key\\rsa_key.pub";
    private String privateFile = "D:\\auth_key\\rsa_key";

    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFile, privateFile, "heima", 2048);
    }

}
