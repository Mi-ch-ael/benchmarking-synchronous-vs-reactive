package ru.etu.stud.java.synchronous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.etu.stud.java.synchronous.services.SingleService;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ((SingleService) ctx.getBean("singleService"))
                .populateRepositoryOnNormalStartup(100, 100);
    }
}
