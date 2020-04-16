package com.wawrze.library.filters;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    public static final String TOKEN_KEY = "TOKEN_KEY";
    public static final String USER_ID_KEY = "USER_ID_KEY";
    public static final String USER_ROLE_KEY = "USER_ROLE_KEY";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Integer sessionUserId = (Integer) req.getSession().getAttribute(USER_ID_KEY);
        String sessionToken = (String) req.getSession().getAttribute(TOKEN_KEY);
        String requestToken = req.getHeader("authorization");

        if (sessionToken == null || sessionUserId == null) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.getWriter().write("Session error!");
        } else if (requestToken == null) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write("Missing token!");
        } else if (!requestToken.equals("Bearer " + sessionToken)) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write("Wrong token!");
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