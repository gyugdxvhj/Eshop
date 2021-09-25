package org.ccunix.eshop.utils;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户登录拦截器  验证用户是否登陆
 */
@WebFilter("/api/auth/*")
public class UserIsLoginUtil implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //在Session中寻找用户的登录信息  如果存在该用户  那么就放行该请求  就返回一个AjaxResult对象  code=505
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session != null){
            Object obj = session.getAttribute("loginMember");
            if (obj != null){
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                AjaxResult result = AjaxResult.error(505,"用户未登陆");
                response.getWriter().write(JSON.toJSONString(result));
            }
        }else {
            AjaxResult result = AjaxResult.error(508,"Session不存在");
            response.getWriter().write(JSON.toJSONString(result));
        }
    }

    @Override
    public void destroy() {

    }
}
