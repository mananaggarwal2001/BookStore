package com.demoproject.bookStoreapplication.DAO;

import com.demoproject.bookStoreapplication.DTO.RegisterUser;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.apache.catalina.User;

public interface UserDAO {
    void addUser(Register user);
    Register findUser(String username);
}
