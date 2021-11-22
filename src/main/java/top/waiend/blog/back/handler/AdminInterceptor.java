package top.waiend.blog.back.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import top.waiend.blog.back.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            if ("0".equals(user.getRole()) && "1".equals(user.getState())) {
                return true;
            }
        }
        request.getRequestDispatcher("/static/error/error.html").forward(request, response);
        return false;
    }
}
