package com.demoproject.bookStoreapplication.Service;

import com.demoproject.bookStoreapplication.DTO.RegisterUser;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ServiceProvider extends UserDetailsService {
   public void RegisterUser(RegisterUser registerUser);
   public Register findUser(String username);
}
