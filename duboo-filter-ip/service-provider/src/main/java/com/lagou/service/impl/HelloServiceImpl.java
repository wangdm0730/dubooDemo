package com.lagou.service.impl;

import com.lagou.service.HelloService;
import ocm.lagou.service.util.DubboTraffic;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl   implements HelloService {
    @Override
    public String sayHello(String name) {
        String ip = DubboTraffic.requestIP();
        System.out.println("hello1 ip "+ip);
        return "hello:"+name;
    }
}
