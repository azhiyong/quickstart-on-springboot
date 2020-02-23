package xyz.mdou.springboot.multirabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.mdou.springboot.multirabbitmq.producer.FirstProducer;
import xyz.mdou.springboot.multirabbitmq.producer.SecondProducer;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MultiRabbitmqApplicationTest {

    @Autowired
    private FirstProducer firstProducer;

    @Autowired
    private SecondProducer secondProducer;

    @Test
    public void testFirstSender() {
        for (int i = 0; i < 100; i++) {
            new Thread(() ->
                    firstProducer.produce("first producer " + new Random().nextInt())
            ).start();
        }
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSecondSender() {
        for (int i = 0; i < 100; i++) {
            new Thread(() ->
                    secondProducer.produce("second producer " + new Random().nextInt())
            ).start();
        }
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}