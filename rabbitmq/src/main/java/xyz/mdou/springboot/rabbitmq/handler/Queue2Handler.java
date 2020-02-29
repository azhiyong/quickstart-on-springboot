package xyz.mdou.springboot.rabbitmq.handler;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.mdou.springboot.rabbitmq.configuration.RabbitMQConstants;
import xyz.mdou.springboot.rabbitmq.message.MessageEntity;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = RabbitMQConstants.QUEUE2)
public class Queue2Handler {

    @RabbitHandler
    public void receive(MessageEntity messageEntity, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("receive message {}, {}", messageEntity, message.getMessageProperties());
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
    }
}
