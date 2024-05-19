package com.demoproject.bookStoreapplication.Security;

import com.demoproject.bookStoreapplication.Service.UserServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserServiceProvider userServiceProvider) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userServiceProvider);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomSuccesshandler customSuccesshandler) throws Exception {
        http.authorizeHttpRequests(config ->
                        config
                                .requestMatchers("/css/**", "/js/**", "/assets/**", "/images/**", "/", "/login", "/register", "/processregistration").permitAll()
                                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customSuccesshandler)
                        .permitAll())
                .logout(logout -> logout.clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll());

        return http.build();
    }
}
