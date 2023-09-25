package shoe.store.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DetailsAccountInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        request.setAttribute("adminInfo",auth);
        request.setAttribute("prefix",String.valueOf(auth.getName().charAt(0)).toUpperCase());
        return true;
    }
}
