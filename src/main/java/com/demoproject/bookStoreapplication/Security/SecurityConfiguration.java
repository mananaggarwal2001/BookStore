package com.demoproject.bookStoreapplication.Security;

import com.demoproject.bookStoreapplication.Service.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.Serial;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(ServiceProvider serviceProvider) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(serviceProvider);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationSuccessHandler CustomSuccesshandler) throws Exception {
        http.authorizeHttpRequests(config ->
                config
                        .requestMatchers("/css/**", "/js/**","/assets/**", "/images/**", "/", "/login", "/register", "/processregistration").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(CustomSuccesshandler)
                        .permitAll())
                .logout(logout->logout.clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll());

        return http.build();
    }
}
