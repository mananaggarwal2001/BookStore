package com.demoproject.bookStoreapplication.Security;

import com.demoproject.bookStoreapplication.Service.UserServiceProvider;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import com.demoproject.bookStoreapplication.databaseClasses.Roles;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomSuccesshandler implements AuthenticationSuccessHandler {
    private final SpringDataWebAutoConfiguration springDataWebAutoConfiguration;
    UserServiceProvider userServiceProvider;

    @Autowired
    public CustomSuccesshandler(UserServiceProvider userServiceProvider, SpringDataWebAutoConfiguration springDataWebAutoConfiguration) {
        this.userServiceProvider = userServiceProvider;
        this.springDataWebAutoConfiguration = springDataWebAutoConfiguration;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String name = authentication.getName();
        Register register = userServiceProvider.findUser(name);
        HttpSession session = request.getSession(); // for fetching the details for the particular user.
        session.setAttribute("username", register.getUsername());
        session.setAttribute("email", register.getEmail());
        List<Roles> getroles = register.getRole();
        List<String> addroles = new ArrayList<>();
        for (Roles temprole : getroles) {
            addroles.add(temprole.getRole());
        }
        session.setAttribute("role", addroles);
        response.sendRedirect("/dashboard?bookid=-1");
    }
}
