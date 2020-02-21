package xyz.mdou.springboot.web.configuration;

import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

@Data
public abstract class AbstractRabbitMQConfiguration {

    private String host;

    private int port;

    private String username;

    private String password;

    private String vhost;

    protected CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                new ConnectionFactory());
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        return connectionFactory;
    }
}
