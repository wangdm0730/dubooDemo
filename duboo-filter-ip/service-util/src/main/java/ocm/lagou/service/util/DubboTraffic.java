package ocm.lagou.service.util;

import org.apache.dubbo.rpc.RpcContext;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 进行在Dubbo环境下的全局获取
 */
public class DubboTraffic {

    public static final String REQUEST_IP = "request_ip";

    /**
     * 获取请求IP地址信息
     */
    public static String requestIP() {
        final Map<String, String> attachments = RpcContext.getContext().getAttachments();
        return attachments.get(REQUEST_IP);
    }

    /**
     * 将Request信息中的IP保存到RPC中
     */
    public static void saveFrom(ServletRequest request) {
        if (request == null) {
            return ;
        }

        final Map<String, String> attachments = RpcContext.getContext().getAttachments();
        attachments.put(REQUEST_IP, request.getRemoteAddr());
    }
}
