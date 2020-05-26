package com.lagou;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;

public class FailMain {
    public static void main(String[] args) {
        RegistryFactory registryFactory =
                ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://192.168.159.159:2181?timeout=15000"));
        registry.register(URL.valueOf("override://0.0.0.0/com.lagou.service.HelloService?category=configurators&dynamic=false&application=service-consumer"));
    }
}
