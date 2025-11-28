package io.github.antoniasousa.icompras.faturamento.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProps {
    private String url;
    private String secretKey;
    private String bucketName;

    public String getAccessKey() {
    }
}
