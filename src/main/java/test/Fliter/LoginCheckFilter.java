package test.Fliter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aliyuncs.http.FormatType.JSON;

/**
 * 检查用户是否已经完成登录
 * 过滤器与拦截器的区别：Filter对所有访问进行增强（在Tomcat服务器进行配置），Interceptor仅针对SpringMVC的访问进行增强
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")  //urlPatterns指定拦截哪些路径
public class LoginCheckFilter implements Filter {
 
    //  此对象的作用：路径匹配器，  匹配路径时支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException {
//      servletRequest向下强制类型转换
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
 
 
        //1. 获取本次请求的URI( URI:请求的资源路径)
        String requestURI = request.getRequestURI();
 
        log.info("拦截到请求:{}", request.getRequestURI());
        // 定义不用处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
 
        //2. 判断本次请求是否需要处理（因为有些请求并不需要用户登录）
        boolean check = check(requestURI, urls);
 
        //3.如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
 
        //4-1.判断登录状态，如果已登录，则直接放行.从session中获取用户，如果获取到说明已经登录
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id为{}", request.getSession().getAttribute("employee"));
 
            Long empId = (Long) request.getSession().getAttribute("employee");
//            BaseContext.setCurrentId(empId);
 
            filterChain.doFilter(request, response);
            return;
        }
 
        //4-2.判断登录状态，如果已登录，则直接放行.从session中获取用户，如果获取到说明已经登录
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户id为{}", request.getSession().getAttribute("user"));
 
            Long userId = (Long) request.getSession().getAttribute("user");
//            BaseContext.setCurrentId(userId);
 
            filterChain.doFilter(request, response);
            return;
        }
 
        //5.如果未登录则返回未登录结果
        log.info("资源路径路径：{}，用户未登录{}", request.getRequestURI(), request.getSession().getAttribute("employee"));
//           通过输出流的方式向客户端响应数据   (为什么要返回这个NOTLOGIN？  因为前端需要这个来进行判定是否登录)
        response.getWriter().write(JSON.toString());
//        filterChain.doFilter(request, response);  加上这个就无法实现
 
    }
 
    /**
     * 检查本次请求是否需要放行
     *
     * @param requestURI 请求的资源路径
     * @param urls       放过的路径
     * @return true 放行
     */
    public boolean check(String requestURI, String[] urls) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
//           放行
                return true;
            }
        }
        return false;
    }
}