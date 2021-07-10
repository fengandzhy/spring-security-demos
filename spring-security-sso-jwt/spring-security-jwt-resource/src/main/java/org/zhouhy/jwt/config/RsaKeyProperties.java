package org.zhouhy.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.zhouhy.jwt.utils.RsaUtils;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@Data
@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties {

    private String publicKeyFile;

    private PublicKey publicKey;

    /**
     * 系统启动的时候触发
     * @throws Exception
     */
    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicKeyFile);        
    }
}
