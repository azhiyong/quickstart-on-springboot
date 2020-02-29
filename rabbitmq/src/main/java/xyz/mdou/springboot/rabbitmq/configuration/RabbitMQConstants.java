package xyz.mdou.springboot.rabbitmq.configuration;

import lombok.Data;

@Data
public class RabbitMQConstants {

    public static final String DIRECT_EXCHANGE = "demo.direct";
    public static final String TOPIC_EXCHANGE = "demo.topic";
    public static final String FANOUT_EXCHANGE = "demo.fanout";

    public static final String DIRECT_ROUTING = "direct";
    public static final String TOPIC_ROUTING1 = "*.topic";
    public static final String TOPIC_ROUTING2 = "topic.#";
    public static final String TOPIC_ROUTING3 = "rabbit.topic";

    public static final String QUEUE1 = "queue1";
    public static final String QUEUE2 = "queue2";
    public static final String QUEUE3 = "queue3";
    public static final String QUEUE4 = "queue4";
}
