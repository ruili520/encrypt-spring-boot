package cn.rl520.encrypt.advice;

import cn.rl520.encrypt.util.Base64Util;
import cn.rl520.encrypt.util.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @Author wenbo
 * @Date 2023/10/13 15:50
 */
public class DecryptHttpInputMessage implements HttpInputMessage{

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private HttpHeaders headers;
    private InputStream body;


    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String privateKey, String charset, boolean showLog) throws Exception {

        if (StringUtils.isEmpty(privateKey)) {
            throw new IllegalArgumentException("privateKey is null");
        }

        this.headers = inputMessage.getHeaders();
        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        String decryptBody;
        if (content.startsWith("{")) {
            log.info("Unencrypted without decryption:{}", content);
            decryptBody = content;
        } else {
            StringBuilder json = new StringBuilder();
            content = content.replaceAll(" ", "+");

            if (!StringUtils.isEmpty(content)) {
                String[] contents = content.split("\\|");
                for (String value : contents) {
                    value = new String(RSAUtil.decrypt(Base64Util.decode(value), privateKey), charset);
                    json.append(value);
                }
            }
            decryptBody = json.toString();
            if(showLog) {
                log.info("Encrypted data received：{},After decryption：{}", content, decryptBody);
            }
        }
        this.body = new ByteArrayInputStream(decryptBody.getBytes());
    }

    @Override
    public InputStream getBody(){
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
