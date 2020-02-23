package xyz.mdou.springboot.multirabbitmq.queue;

public interface MessageProducer<T> {

    void produce(T t);
}
