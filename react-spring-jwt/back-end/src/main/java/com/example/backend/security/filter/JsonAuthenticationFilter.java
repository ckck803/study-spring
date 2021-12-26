package com.example.backend.security.filter;

import com.example.backend.entity.dto.LoginDto;
import com.example.backend.security.token.JsonAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_PROCESSING_URL = "/api/login";

    @Autowired
    private ObjectMapper objectMapper;

    public JsonAuthenticationFilter() {
        super(new AntPathRequestMatcher(LOGIN_PROCESSING_URL));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!isApplicationJSON(request)){
            throw new IllegalStateException("Content Type is not Application/json");
        }
        LoginDto loginDto = objectMapper.readValue(request.getReader(), LoginDto.class);

        if(ObjectUtils.isEmpty(loginDto.getUsername()) || ObjectUtils.isEmpty(loginDto.getPassword())){
            throw new IllegalArgumentException("Username or Password is empty");
        }

        JsonAuthenticationToken jsonAuthenticationToken = new JsonAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return getAuthenticationManager().authenticate(jsonAuthenticationToken);
    }

    private boolean isApplicationJSON(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getHeader("Content-type").equals(MediaType.APPLICATION_JSON_VALUE)){
            return true;
        }
        return false;
    }
}
