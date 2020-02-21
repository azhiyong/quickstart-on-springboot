package xyz.mdou.springboot.configuration;

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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties("rabbit.mq.second")
public class SecondRabbitMQConfiguration extends AbstractRabbitMQConfiguration {

    public static final String CALLBACK_QUEUE = "CALLBACK_QUEUE";
    public static final String CALLBACK_ROUTING_KEY = "CALLBACK_ROUTING_KEY";

    public static final String EXCHANGE = "second";

    @Bean(name = "secondConnectionFactory")
    public ConnectionFactory secondConnectionFactory() {
        return super.connectionFactory();
    }

    @Bean(name = "secondRabbitTemplate")
    public RabbitTemplate secondRabbitTemplate(@Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setChannelTransacted(false);
        template.setExchange(EXCHANGE);
        template.setEncoding(StandardCharsets.UTF_8.displayName());
        return template;
    }

    @Bean(name = "secondListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory secondListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
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

    @Bean(name = "secondAmqpAdmin")
    public AmqpAdmin secondAmqpAdmin(@Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean(name = "secondDeclarables")
    public List<Declarable> secondDeclarables(@Qualifier("secondAmqpAdmin") AmqpAdmin amqpAdmin) {
        DirectExchange exchange = new DirectExchange(EXCHANGE);
        exchange.setAdminsThatShouldDeclare(amqpAdmin);

        Queue callbackQueue = new Queue(CALLBACK_QUEUE);
        callbackQueue.setAdminsThatShouldDeclare(amqpAdmin);

        Binding callbackBinding = BindingBuilder.bind(callbackQueue)
                .to(exchange).with(CALLBACK_ROUTING_KEY);
        callbackBinding.setAdminsThatShouldDeclare(amqpAdmin);

        return Arrays.asList(exchange, callbackQueue, callbackBinding);
    }
}
