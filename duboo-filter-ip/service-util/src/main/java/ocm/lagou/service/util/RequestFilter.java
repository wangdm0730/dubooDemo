package ocm.lagou.service.util;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤请求，并且将其中的{@link ServletRequest}信息进行保留到
 */
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // 设置请求信息到当前线程
            WebRequestHolder.setting(servletRequest);
            filterChain.doFilter(servletRequest,servletResponse);
        } finally {
            WebRequestHolder.remove();
        }
    }

    @Override
    public void destroy() {
    }
}
