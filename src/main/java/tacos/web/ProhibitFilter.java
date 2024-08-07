package tacos.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class ProhibitFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          if(Objects.equals(request.getHeader("x-prohibit"),"no")){
              //rejectrequest
              response.setStatus(HttpStatus.FORBIDDEN.value());
              response.setCharacterEncoding("UTF-8");
              response.setHeader("Content-Type", "text/plain;charset=UTF-8");
              response.getWriter().write("🚫🚫🚫🚫 This is probitted");
              return;
          }
          filterChain.doFilter(request, response);
    }
}
