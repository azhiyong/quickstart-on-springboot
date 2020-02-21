package xyz.mdou.springboot.web.configuration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MQConfigurationTest {

    @Autowired
    @Qualifier(FirstRabbitMQConfiguration.FIRST_RABBIT_TEMPLATE)
    private RabbitTemplate rabbitmqTemplate;

    @Test
    public void testSend() {
        rabbitmqTemplate.convertAndSend(FirstRabbitMQConfiguration.API_ROUTING_KEY, "test");
    }
}