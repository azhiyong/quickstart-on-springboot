package xyz.mdou.springboot.rabbitmq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        rabbitConnectionFactory.setPublisherReturns(true);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                log.info("confirm callback {}, {}, {}", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.info("return callback {}, {}, {}, {}, {}", message, replyCode, replyText, exchange, routingKey));
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitMQConstants.DIRECT_EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMQConstants.TOPIC_EXCHANGE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConstants.FANOUT_EXCHANGE);
    }

    @Bean
    public Queue queue1() {
        return new Queue(RabbitMQConstants.QUEUE1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(RabbitMQConstants.QUEUE2);
    }

    @Bean
    public Queue queue3() {
        return new Queue(RabbitMQConstants.QUEUE3);
    }

    @Bean
    public Queue queue4() {
        return new Queue(RabbitMQConstants.QUEUE4);
    }

    @Bean
    public Binding bindingQueue1Direct() {
        return BindingBuilder.bind(queue1()).to(directExchange())
                .with(RabbitMQConstants.DIRECT_ROUTING);
    }

    @Bean
    public Binding bindingQueue2Fanout() {
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingQueue3Fanout() {
        return BindingBuilder.bind(queue3()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutTopic() {
        return BindingBuilder.bind(fanoutExchange()).to(topicExchange())
                .with(RabbitMQConstants.TOPIC_ROUTING1);
    }

    @Bean
    public Binding bindingQueue3Topic() {
        return BindingBuilder.bind(queue3()).to(topicExchange())
                .with(RabbitMQConstants.TOPIC_ROUTING2);
    }

    @Bean
    public Binding bindingQueue4Topic() {
        return BindingBuilder.bind(queue4()).to(topicExchange())
                .with(RabbitMQConstants.TOPIC_ROUTING3);
    }
}
