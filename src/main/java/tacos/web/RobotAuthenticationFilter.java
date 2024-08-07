package tacos.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class RobotAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       if(!Collections.list(request.getHeaderNames()).contains("x-robot-secret")){
            filterChain.doFilter(request, response);
            return;
        }

        if(!Objects.equals(request.getHeader("x-robot-secret"), "beep-boop")){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/plain;charset=UTF-8");
            response.getWriter().write("🚫🚫🚫🚫 You are not Ms Robot");
        }
        //we are going to authenticate
        //we need an authentication object
       /* var auth = new RobotAuthenticationToken();
        var newContext = SecurityContextHolder.getContext();
        newContext.setAuthentication(auth);
        SecurityContextHolder.setContext(newContext);*/

        //Used for logging through requests
        /*SecurityContextRepository scr;
        scr.saveContext(newContext, request, response);*/
        filterChain.doFilter(request, response);

    }
}
