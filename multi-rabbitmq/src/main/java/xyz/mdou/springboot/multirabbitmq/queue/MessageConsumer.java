package xyz.mdou.springboot.multirabbitmq.queue;

public interface MessageConsumer<T> {

    void receive(T t);
}
