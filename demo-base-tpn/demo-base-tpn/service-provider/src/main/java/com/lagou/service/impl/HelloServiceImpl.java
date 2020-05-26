package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Random;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name, int timeToWait) {
        int random = new Random().nextInt(timeToWait);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello:"+name;
    }


    @Override
    public String sayHello2(String name, int timeToWait) {
        int random = new Random().nextInt(timeToWait);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello2:" +name ;
    }

    @Override
    public String sayHello3(String name, int timeToWait) {
        int random = new Random().nextInt(timeToWait);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello3:" +name ;
    }
}
