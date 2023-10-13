package cn.rl520.encrypt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wenbo
 * @Date 2023/10/13 15:51
 */
@ConfigurationProperties(prefix = "rsa.encrypt")
@Configuration
@Data
public class SecretKeyConfig {

    private String privateKey;

    private String publicKey;

    private String charset = "UTF-8";

    private boolean open = true;

    private boolean showLog = false;

}
