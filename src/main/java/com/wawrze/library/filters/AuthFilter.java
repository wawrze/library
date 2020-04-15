package com.wawrze.library.filters;

import com.wawrze.library.domains.users.User;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    public static final String TOKEN_KEY = "TOKEN_KEY";
    public static final String USER_KEY = "USER_KEY";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        User sessionUser = (User) req.getSession().getAttribute(USER_KEY);
        String sessionToken = (String) req.getSession().getAttribute(TOKEN_KEY);
        String requestToken = req.getHeader("token");

        if (sessionToken == null || requestToken == null) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write("Missing token!");
        }
        else if (!sessionToken.equals(requestToken)) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write("Wrong token!");
        }
        else if (sessionUser == null) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.getWriter().write("Session error!");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Do nothing
    }

    @Override
    public void destroy() {
        // Do nothing
    }

}