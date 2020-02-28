package xyz.mdou.springboot.zookeeper.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("zookeeper")
public class ZookeeperProperties {

    private String url;

    private int timeout = 1000;

    private int retry = 3;
}
