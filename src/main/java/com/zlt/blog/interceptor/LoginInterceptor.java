package com.zlt.blog.interceptor;

import com.zlt.blog.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
//        if (request.getSession().getAttribute("user") == null){
//            response.sendRedirect("/admin");
//            return false;
//        }
        if (user==null){
            response.sendRedirect("/admin");
            return false;
        }
        if (user.getType()!=1){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
