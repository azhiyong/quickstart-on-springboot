package xyz.mdou.springboot.email.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import xyz.mdou.springboot.email.EmailApplicationTest;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

@Slf4j
public class MailServiceTest extends EmailApplicationTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSendSimpleMail() {
        mailService.sendSimpleMail("zzzzhyli@gmail.com",
                "send simple mail test",
                "This is a test of send simple mail");
    }

    @Test
    public void testSendHtmlMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("author", "amaodou");
        context.setVariable("url", "https://github.com/amaoudou");
        String content = templateEngine.process("index", context);
        mailService.sendHtmlMail("zzzzhyli@gmail.com",
                "send html mail test", content);
    }

    @Test
    public void testSendAttachmentsMail() throws MessagingException, FileNotFoundException {
        File resource = ResourceUtils.getFile("classpath:templates/index.html");
        Context context = new Context();
        context.setVariable("author", "amaodou");
        context.setVariable("url", "https://github.com/amaoudou");
        String content = templateEngine.process("index", context);
        mailService.sendAttachmentsMail("zzzzhyli@gmail.com",
                "send attachment mail test", content,
                resource.getPath());
    }
}