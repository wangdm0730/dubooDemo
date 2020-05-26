package ocm.lagou.service.util;

import javax.servlet.ServletRequest;

/**
 * 用于对{@link javax.servlet.ServletRequest}数据进行保存到当前线程中，可以再web环境下获取
 */
public class WebRequestHolder {

    private static ThreadLocal<ServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取当前请求信息
     * @return
     */
    public static ServletRequest request() {
        return REQUEST_THREAD_LOCAL.get();
    }

    /**
     * 保存当前请求到当前线程
     * @param request
     */
    static void setting(ServletRequest request) {
        REQUEST_THREAD_LOCAL.set(request);
    }

    /**
     * 从当前线程中移除请求的引用
     */
    static void remove() {
        REQUEST_THREAD_LOCAL.remove();
    }
}
