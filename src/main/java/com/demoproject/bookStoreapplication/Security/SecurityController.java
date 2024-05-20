package com.demoproject.bookStoreapplication.Security;
import com.demoproject.bookStoreapplication.DTO.RegisterUser;
import com.demoproject.bookStoreapplication.Service.UserServiceProvider;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.logging.Logger;

@Controller
public class SecurityController {
    Logger logger = Logger.getLogger(SecurityController.class.getName());
    UserServiceProvider userServiceProvider;

    @InitBinder
    public void initbinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    public SecurityController(UserServiceProvider userServiceProvider) {
        this.userServiceProvider = userServiceProvider;
    }

    @GetMapping("/login")
    public String login(Model themodel) {
        themodel.addAttribute("error", "Invalid Credentials!!!!!!!");
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
        Register user = userServiceProvider.findUser(username);
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
        userServiceProvider.RegisterUser(registerUser);
        themodel.addAttribute("email", registerUser.getEmail());
        themodel.addAttribute("username", registerUser.getUsername());
        themodel.addAttribute("firstName", registerUser.getFirstName());
        themodel.addAttribute("lastName", registerUser.getLastName());
        return "registrationConfirmation";
    }
}