package xyz.mdou.springboot.queue;

public interface MessageProducer<T> {

    void produce(T t);
}
