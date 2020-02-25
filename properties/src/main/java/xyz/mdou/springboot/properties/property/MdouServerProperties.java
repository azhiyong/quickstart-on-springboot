package xyz.mdou.springboot.properties.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties("mdou")
public class MdouServerProperties {

    private Map<String, MdouServerProperty> servers;

    @Data
    public static class MdouServerProperty {
        private String ip;
        private List<String> nameservers;
    }
}


