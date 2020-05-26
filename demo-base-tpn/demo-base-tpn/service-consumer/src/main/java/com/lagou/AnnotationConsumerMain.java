package com.lagou;

import com.lagou.bean.ConsumerComponent;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;


public class AnnotationConsumerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        ConsumerComponent service = context.getBean(ConsumerComponent.class);
        while (true) {
            //System.in.read();
            try {
                
                String hello = service.sayHello("world", 100);
               // System.out.println("result :" + hello);
                String hello2 = service.sayHello2("world", 100);
                //System.out.println("result :" + hello2);
                String hello3 = service.sayHello3("world", 100);
                //System.out.println("result :" + hello3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    //@EnableDubbo(scanBasePackages = "bean")
    @ComponentScan("com.lagou.bean")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
