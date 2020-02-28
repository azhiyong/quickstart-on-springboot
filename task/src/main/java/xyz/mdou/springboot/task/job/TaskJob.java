package xyz.mdou.springboot.task.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TaskJob {

    @Scheduled(cron = "0 * * * * ?")
    public void perMinute() {
        log.info("run cron {}", LocalDateTime.now());
    }

    @Scheduled(fixedRate = 3000)
    public void fixedRate() {
        log.info("run fixedRate {}", LocalDateTime.now());
    }
}
