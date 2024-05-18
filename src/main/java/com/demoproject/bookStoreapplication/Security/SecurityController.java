package com.demoproject.bookStoreapplication.Security;

import com.demoproject.bookStoreapplication.DTO.RegisterUser;
import com.demoproject.bookStoreapplication.Service.ServiceProvider;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;

@Controller
public class SecurityController {
    Logger logger = Logger.getLogger(SecurityController.class.getName());
    ServiceProvider serviceProvider;

    @Autowired
    public SecurityController(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(Model themodel) {
        themodel.addAttribute("user", new RegisterUser());
        return "register";
    }

    @PostMapping("/processregistration")
    public String processregistration(@ModelAttribute("user") RegisterUser registerUser, Model themodel, BindingResult result) {
        String username = registerUser.getUsername();
        logger.info("finding the user with the username:- " + username);
        Register user = serviceProvider.findUser(username);
        if (user != null) {
            logger.warning("User Already Exists!!!!!!");
            themodel.addAttribute("error", "User Already Exists!!!!!!");
            themodel.addAttribute("user", new RegisterUser());
            return "redirect:/register";
        }
        if (result.hasErrors()) {
            logger.warning("Invalid Credentials");
            themodel.addAttribute("error", "Invalid Credentials!!!!!!!");
            themodel.addAttribute("user", new RegisterUser());
            return "redirect:/register";
        }
        logger.info("Saving the user with the username:- " + username);
        serviceProvider.RegisterUser(registerUser);
        return "redirect:/register";
    }
}