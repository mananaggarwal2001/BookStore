package com.demoproject.bookStoreapplication.Security;

import com.demoproject.bookStoreapplication.Service.ServiceProvider;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccesshandler implements AuthenticationSuccessHandler {
    ServiceProvider serviceProvider;

    @Autowired
    public CustomSuccesshandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String name = authentication.getName();
        Register register = serviceProvider.findUser(name);
        HttpSession session = request.getSession(); // for fetching the details for the particular user.
        session.setAttribute("username", register.getUsername());
        session.setAttribute("email", register.getEmail());

    }
}
