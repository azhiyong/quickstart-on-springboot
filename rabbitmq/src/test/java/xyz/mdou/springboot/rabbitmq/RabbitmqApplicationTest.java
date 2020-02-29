package xyz.mdou.springboot.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.mdou.springboot.rabbitmq.configuration.RabbitMQConstants;
import xyz.mdou.springboot.rabbitmq.message.MessageEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitmqApplicationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDirectExchange() {
        rabbitTemplate.convertAndSend(RabbitMQConstants.DIRECT_EXCHANGE, RabbitMQConstants.DIRECT_ROUTING,
                new MessageEntity("direct message " + RabbitMQConstants.DIRECT_ROUTING));
    }

    @Test
    public void testTopicExchange1() {
        String routingKey1 = "first.topic";
        rabbitTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE, routingKey1,
                new MessageEntity("topic message " + routingKey1));
    }

    @Test
    public void testTopicExchange2() {
        String routingKey2 = "topic.test.first";
        rabbitTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE, routingKey2,
                new MessageEntity("topic message " + routingKey2));
    }

    @Test
    public void testTopicExchange3() {
        String routingKey3 = RabbitMQConstants.TOPIC_ROUTING3;
        rabbitTemplate.convertAndSend(RabbitMQConstants.TOPIC_EXCHANGE, routingKey3,
                new MessageEntity("topic message " + routingKey3));
    }

    @Test
    public void testFanoutExchange() {
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, "",
                new MessageEntity("fanout message"));
    }
}