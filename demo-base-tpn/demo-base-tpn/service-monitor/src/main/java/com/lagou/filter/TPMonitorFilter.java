package com.lagou.filter;

import com.lagou.bean.MethodInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter ,Runnable {
 
    Map<String, List<MethodInfo>>  methodTimes = new ConcurrentHashMap<>();

    public TPMonitorFilter(){
        // 每隔5秒打印线程使用情况
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(this, 5,5, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(new Thread(new RemoveDataThread(methodTimes)), 10,60, TimeUnit.SECONDS);
    }
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 获取执行方法名
        Result result = null;
        Long takeTime = 0L;
        try
        {
            Long startTime = System.currentTimeMillis();
            result = invoker.invoke(invocation);
            if (result.getException() instanceof Exception)
            {
                throw new Exception(result.getException());
            }
            takeTime = System.currentTimeMillis() - startTime;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return result;
        }
        //System.out.println("method="+invocation.getMethodName() + "消耗的时间:"+takeTime +"毫秒");
        String methodName = invocation.getMethodName();
        List<MethodInfo> methodInfos =  methodTimes.get(methodName);
        if (methodInfos == null){
            methodInfos = new ArrayList<>();
            methodTimes.put(methodName,methodInfos);
        }
        methodInfos.add(new MethodInfo(invocation.getMethodName(),takeTime,System.currentTimeMillis()));

        return  result;

    }
    // rate 代表百分比 90 传入 0.9 即可  99 就是 0.99
    private  long  getTP(List<MethodInfo> methodInfos,double  rate){
       // System.out.println("getTp");
          // 构建一个临时数组保存 满足1一分钟之内的数据
          List<MethodInfo>  sortInfo = new ArrayList<>();
        // 计算最近一分钟的TP90 和 TP99
        long  endTime = System.currentTimeMillis();
        long  startTime = System.currentTimeMillis() - 60000;
        // 遍历列表集合
        int  length = methodInfos.size();
        for (int i=0;i<length;i++){
            //System.out.println("#######");
            MethodInfo  methodInfo = methodInfos.get(i);
            if (methodInfo.getEndTimes() >= startTime && methodInfo.getEndTimes() <= endTime){
                 sortInfo.add(methodInfo);
            }
        }
        sortInfo.sort(new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                if(o1.getTimes() > o2.getTimes()){
                    return  1;
                }else if(o1.getTimes() < o2.getTimes()){
                    return -1;
                }else{
                    return  0;
                }

            }
        });
        int  index = (int)(sortInfo.size() * rate);

        return sortInfo.get(index).getTimes();
    }
    @Override
    public void run() {
        Date date = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = sdf.format(date);
        for(Map.Entry<String,List<MethodInfo>> methodInfos : methodTimes.entrySet()){
            System.out.println(dateStr+ methodInfos.getKey() +"的TP90:" + getTP(methodInfos.getValue(),0.9) + "毫秒,"
                    + "TP99:" + getTP(methodInfos.getValue(),0.99)+ "毫秒" );
        }

    }
}
