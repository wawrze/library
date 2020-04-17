package com.wawrze.library.filters;

import com.wawrze.library.dao.UserDAO;
import com.wawrze.library.domains.users.User;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    public static final String USER_ID_KEY = "USER_ID_KEY";
    public static final String USER_ROLE_KEY = "USER_ROLE_KEY";

    private UserDAO userDAO;

    public AuthFilter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        User user = null;
        try {
            String tokenHeader = req.getHeader("authorization");
            String[] split = tokenHeader.split("Bearer ");
            String requestToken = split[1];
            user = userDAO.findByToken(requestToken);
        } catch (Exception e) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write("Incorrect token format!");
        }

        if (user == null) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write("Wrong token!");
        } else {
            req.getSession().setAttribute(USER_ID_KEY, user.getId());
            req.getSession().setAttribute(USER_ROLE_KEY, user.getUserRole());
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