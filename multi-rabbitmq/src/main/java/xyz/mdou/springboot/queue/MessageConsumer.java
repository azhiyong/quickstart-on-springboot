package xyz.mdou.springboot.queue;

public interface MessageConsumer<T> {

    void receive(T t);
}
