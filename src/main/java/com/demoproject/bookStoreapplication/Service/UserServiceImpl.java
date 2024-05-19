package com.demoproject.bookStoreapplication.Service;

import com.demoproject.bookStoreapplication.databaseClasses.Roles;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.demoproject.bookStoreapplication.DAO.UserDAO;
import com.demoproject.bookStoreapplication.DTO.RegisterUser;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceProvider {
    UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void RegisterUser(RegisterUser registerUser) {
        Register user = new Register();
        Roles findRole = userDAO.findRoleByName("ROLE_USER");
        List<Roles> rolelist = new ArrayList<>();
        rolelist.add(findRole);
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setUsername(registerUser.getUsername());
        user.setRole(rolelist);
        user.setEnabled(true);
        userDAO.addUser(user);
    }

    @Override
    public Register findUser(String username) {
        return userDAO.findUser(username);
    }

    @Override
    @Transactional
    public void addUpdatedUser(Register register) {
        userDAO.addUser(register);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register register = userDAO.findUser(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userDAO.findRoleByName("ROLE_USER").getRole()));
        return new User(register.getUsername(), register.getPassword(), authorities);
    }
}