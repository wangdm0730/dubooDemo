package com.lagou;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class Provider2Main {
    public static void main(String[] args) throws  Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        System.in.read();
    }
    @Configuration
    @EnableDubbo(scanBasePackages = "com.lagou.service")
    @PropertySource("classpath:/provider2.properties")
    static  class  ProviderConfiguration{
        @Bean
        public RegistryConfig registryConfig(){
            RegistryConfig  registryConfig  = new RegistryConfig();
            registryConfig.setAddress("zookeeper://192.168.159.159:2181?timeout=10000");
            return   registryConfig;
        }
    }
}
