package org.zhouhy.springsecurity.key;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.zhouhy.springsecurity.utils.RsaUtils;

import javax.annotation.PostConstruct;
import java.security.PublicKey;


@Data
@ConfigurationProperties(prefix = "heima.config")
@Component
public class RsaPubKeyProperties {

    private String pubKeyPath;
    private PublicKey publicKey;


    @PostConstruct
    public void loadKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }

}
