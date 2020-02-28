package xyz.mdou.springboot.zookeeper.configuration;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperConfiguration {

    @Autowired
    private ZookeeperProperties properties;

    @Bean
    public CuratorFramework curatorFrameworkFactory() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(
                properties.getTimeout(), properties.getRetry());
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                properties.getUrl(), retryPolicy);
        client.start();
        return client;
    }
}
