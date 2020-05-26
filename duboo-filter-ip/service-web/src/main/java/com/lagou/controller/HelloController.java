package com.lagou.controller;

import com.lagou.service.HelloService;
import com.lagou.service.HelloService2;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    @Reference
    private HelloService  helloService;
    @Reference
    private HelloService2  helloService2;
    @RequestMapping("/hello.do")
    @ResponseBody
    public  String  toHello(HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        helloService.sayHello(remoteAddr);
        helloService2.sayHello2(remoteAddr);
        return "hello";
    }
}
