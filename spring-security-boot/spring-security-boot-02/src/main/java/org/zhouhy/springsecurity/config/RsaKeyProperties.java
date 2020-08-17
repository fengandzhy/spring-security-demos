package org.zhouhy.springsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.zhouhy.springsecurity.utils.RsaUtils;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@ConfigurationProperties(prefix = "heima.key")
public class RsaKeyProperties {

    private String pubKeyPath;
    private String priKeyPath;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void loadKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
        privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

}
