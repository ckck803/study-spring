package com.example.backend.security.config;

import com.example.backend.repository.UserInfoRepository;
import com.example.backend.security.filter.JsonAuthenticationFilter;
import com.example.backend.security.handler.JsonAuthenticationSuccessHandler;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.provider.JsonAuthenticationProvider;
import com.example.backend.security.servcie.UserInfoService;
import com.example.backend.security.servcie.UserInfoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(0)
@RequiredArgsConstructor
public class JsonSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserInfoRepository userInfoRepository;
    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider())
                .userDetailsService(userInfoService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .addFilterBefore(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserInfoService userInfoService() {
        return new UserInfoServiceImpl(userInfoRepository, passwordEncoder());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new JsonAuthenticationProvider(passwordEncoder(), userDetailsService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler(){
        return new JsonAuthenticationSuccessHandler(objectMapper, jwtUtils);
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter();
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(jsonAuthenticationSuccessHandler());

        return jsonAuthenticationFilter;
    }
}
