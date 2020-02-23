package xyz.mdou.springboot.properties.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("author")
public class AuthorProperty {

    private String name;
    private String homepage;
}
