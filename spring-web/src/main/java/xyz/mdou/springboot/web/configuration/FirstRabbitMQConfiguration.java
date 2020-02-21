package xyz.mdou.springboot.web.configuration;

import com.google.gson.JsonSyntaxException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties("rabbit.mq.first")
public class FirstRabbitMQConfiguration extends AbstractRabbitMQConfiguration {

    private static final String EXCHANGE = "first";

    public static final String FIRST_RABBIT_TEMPLATE = "firstRabbitTemplate";

    public static final String API_QUEUE = "API_QUEUE";
    public static final String API_ROUTING_KEY = "API_ROUTING_KEY";

    public static final String SCHEDULER_QUEUE = "SCHEDULER_QUEUE";
    public static final String SCHEDULER_ROUTING_KEY = "SCHEDULER_ROUTING_KEY";

    @Primary
    @Bean(name = "firstConnectionFactory")
    public ConnectionFactory firstConnectionFactory() {
        return super.connectionFactory();
    }

    @Primary
    @Bean(name = FIRST_RABBIT_TEMPLATE)
    public RabbitTemplate firstRabbitTemplate(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setChannelTransacted(true);
        template.setExchange(EXCHANGE);
        template.setEncoding(StandardCharsets.UTF_8.displayName());
        return template;
    }

    @Bean(name = "firstListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory firstListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(
                new ConditionalRejectingErrorHandler.DefaultExceptionStrategy() {
                    @Override
                    protected boolean isUserCauseFatal(Throwable cause) {
                        return cause instanceof JsonSyntaxException
                                || cause instanceof NullPointerException;
                    }
                }));
        return factory;
    }

    @Bean(name = "firstAmqpAdmin")
    public AmqpAdmin firstAmqpAdmin(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean(name = "firstDirectExchange")
    public DirectExchange firstDirectExchange(@Qualifier("firstAmqpAdmin") AmqpAdmin amqpAdmin) {
        DirectExchange exchange = new DirectExchange(EXCHANGE);
        exchange.setAdminsThatShouldDeclare(amqpAdmin);
        return exchange;
    }

    @Bean(name = "firstDeclarables")
    public List<Declarable> firstDeclarables(@Qualifier("firstAmqpAdmin") AmqpAdmin amqpAdmin,
                                             @Qualifier("firstDirectExchange") DirectExchange exchange) {
        Queue apiQueue = new Queue(API_QUEUE);
        apiQueue.setAdminsThatShouldDeclare(amqpAdmin);

        Binding apiBinding = BindingBuilder.bind(apiQueue)
                .to(exchange).with(API_ROUTING_KEY);
        apiBinding.setAdminsThatShouldDeclare(amqpAdmin);

        Queue schedulerQueue = new Queue(SCHEDULER_QUEUE);
        schedulerQueue.setAdminsThatShouldDeclare(amqpAdmin);

        Binding schedulerBinding = BindingBuilder.bind(schedulerQueue)
                .to(exchange).with(SCHEDULER_ROUTING_KEY);
        schedulerBinding.setAdminsThatShouldDeclare(amqpAdmin);

        return Arrays.asList(apiQueue, schedulerQueue, apiBinding,
                schedulerBinding);
    }

}