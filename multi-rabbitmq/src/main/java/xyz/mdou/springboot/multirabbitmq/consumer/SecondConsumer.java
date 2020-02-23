package xyz.mdou.springboot.multirabbitmq.consumer;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.mdou.springboot.multirabbitmq.configuration.SecondRabbitMQConfiguration;
import xyz.mdou.springboot.multirabbitmq.queue.MessageConsumer;

@Slf4j
@Component
public class SecondConsumer implements MessageConsumer<Object> {

    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(SecondRabbitMQConfiguration.CALLBACK_QUEUE),
            exchange = @Exchange(SecondRabbitMQConfiguration.EXCHANGE),
            key = SecondRabbitMQConfiguration.CALLBACK_ROUTING_KEY),
            containerFactory = "secondListenerContainerFactory")
    public void receive(Object o) {
        log.info("receive message from {}, {}",
                SecondRabbitMQConfiguration.CALLBACK_QUEUE,
                new Gson().toJson(o));
    }
}
