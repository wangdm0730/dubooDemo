package ocm.lagou.service.util;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * 当发出请求时，将当前的请求IP地址信息进行传递给下游, 自动在consumer端生效
 */
@Activate(group = {CommonConstants.CONSUMER })
public class TransportIpFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 保存IP地址信息
        DubboTraffic.saveFrom(WebRequestHolder.request());
        return invoker.invoke(invocation);
    }

}
