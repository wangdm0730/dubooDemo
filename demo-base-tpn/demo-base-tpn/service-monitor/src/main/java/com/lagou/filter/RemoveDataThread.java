package com.lagou.filter;

import com.lagou.bean.MethodInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoveDataThread implements  Runnable{
    private  Map<String, List<MethodInfo>>   methodTimes;

    public  RemoveDataThread(Map<String, List<MethodInfo>>  methodTimes ){
        this.methodTimes = methodTimes;
    }
    @Override
    public void run() {
        System.out.println("---删除过期数据---");
        for(Map.Entry<String, List<MethodInfo>> methodInfos : methodTimes.entrySet()){
            List<MethodInfo> methodList = methodInfos.getValue();
            Iterator<MethodInfo> iterator = methodList.iterator();
            Long   removeCon  = System.currentTimeMillis() - 60000;
            while (iterator.hasNext()){
                  MethodInfo  info = iterator.next();
                  if(info.getEndTimes() < removeCon){
                      iterator.remove();
                  }
            }
        }
    }
}
