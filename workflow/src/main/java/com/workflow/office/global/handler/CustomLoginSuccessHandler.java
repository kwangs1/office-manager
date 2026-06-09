package com.workflow.office.global.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.workflow.office.user.domain.CustomUserDetails;
import com.workflow.office.user.domain.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
    	
    	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    	User user = userDetails.getUser();
        HttpSession session = request.getSession();

        session.setAttribute("loginUserId", user.getId());
        session.setAttribute("loginUserNm", user.getName()); 
        
        response.sendRedirect("/calendars");
    }
}