package com.lagou;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class MainZk {
    public static void main(String[] args) throws Exception {
        init();
    }
    public static void init() throws Exception {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder().
                connectString("zookeeper://192.168.159.159:2181?timeout=15000").
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        zkClient.start();

        if (zkClient.checkExists().forPath("/dubbo/config/dubbo/dubbo.properties") == null) {
            zkClient.create().creatingParentsIfNeeded().forPath("/dubbo/config/dubbo/dubbo.properties");
        }
        zkClient.setData().forPath("/dubbo/config/dubbo/dubbo.properties", ("dubbo.registry.address=zookeeper://192.168.159.159:2181?timeout=15000\n" +
                "dubbo.metadata-report.address=zookeeper://192.168.159.159:2181?timeout=15000").getBytes());
    }

}
