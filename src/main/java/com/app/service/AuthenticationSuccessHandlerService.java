package com.app.service;

import com.app.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@Component
@SessionScope
public class AuthenticationSuccessHandlerService implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessHandlerService.class);

    @Autowired
    private HttpSession session;
    @Autowired
    private UserDAO userDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = null;
        if (authentication.getPrincipal() instanceof Principal) {
            username = ((Principal) authentication.getPrincipal()).getName();
            LOGGER.warn(username + " failed authentication");
        } else {
            username = ((User) authentication.getPrincipal()).getUsername();
            com.app.models.User user = userDAO.findByName(username);
            session.setAttribute("user_id", user.getId());
            LOGGER.info(username + " successfully logged in");
        }
        response.sendRedirect("/main/id/0");
    }
}
