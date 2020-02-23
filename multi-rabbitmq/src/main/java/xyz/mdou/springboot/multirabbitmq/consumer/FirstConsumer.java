package xyz.mdou.springboot.multirabbitmq.consumer;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.mdou.springboot.multirabbitmq.configuration.FirstRabbitMQConfiguration;
import xyz.mdou.springboot.multirabbitmq.queue.MessageConsumer;

@Slf4j
@Component
public class FirstConsumer implements MessageConsumer<Object> {

    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(FirstRabbitMQConfiguration.API_QUEUE),
            exchange = @Exchange(FirstRabbitMQConfiguration.EXCHANGE),
            key = FirstRabbitMQConfiguration.API_ROUTING_KEY),
            containerFactory = "firstListenerContainerFactory")
    public void receive(Object o) {
        log.info("receive message from {}, {}",
                FirstRabbitMQConfiguration.API_QUEUE,
                new Gson().toJson(o));
    }
}
