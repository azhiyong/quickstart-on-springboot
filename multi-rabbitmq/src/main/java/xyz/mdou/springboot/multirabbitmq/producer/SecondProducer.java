package xyz.mdou.springboot.multirabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import xyz.mdou.springboot.multirabbitmq.configuration.SecondRabbitMQConfiguration;
import xyz.mdou.springboot.multirabbitmq.queue.MessageProducer;

@Slf4j
@Component
public class SecondProducer implements MessageProducer<String> {

    @Autowired
    @Qualifier("secondRabbitTemplate")
    public RabbitTemplate secondRabbitTemplate;

    @Override
    public void produce(String s) {
        secondRabbitTemplate.convertAndSend(SecondRabbitMQConfiguration.CALLBACK_ROUTING_KEY, s);
    }
}
