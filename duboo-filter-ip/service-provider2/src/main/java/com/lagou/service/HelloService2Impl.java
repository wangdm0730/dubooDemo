package com.lagou.service;

import ocm.lagou.service.util.DubboTraffic;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloService2Impl implements  HelloService2 {
    @Override
    public String sayHello2(String name) {
        String ip = DubboTraffic.requestIP();
        System.out.println("hello 2 ip " + ip );
        return "hello2 "+name;
    }
}
