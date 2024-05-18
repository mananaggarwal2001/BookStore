package com.demoproject.bookStoreapplication.Service;

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
public class ServiceImpl implements ServiceProvider {
    UserDAO userDAO;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ServiceImpl(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void RegisterUser(RegisterUser registerUser) {
        Register user = new Register();
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setUsername(registerUser.getUsername());
        user.setEnabled(true);
        userDAO.addUser(user);
    }

    @Override
    public Register findUser(String username) {
        return userDAO.findUser(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register register = userDAO.findUser(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(register.getUsername(), register.getPassword(), authorities);
    }
}