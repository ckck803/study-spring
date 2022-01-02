package com.example.backend.security.filter;

import com.example.backend.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final JwtUtils jwtUtils;
    @Value("${security.url.login}")
    public String loginUrl;
    @Value("${security.url.signup}")
    public String signupUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        RequestMatcher loginRequestMatcher = new AntPathRequestMatcher(loginUrl);
        RequestMatcher signupRequestMatcher = new AntPathRequestMatcher(signupUrl);

        if (loginRequestMatcher.matches(request)) {
            log.info("로그인 시도입니다.JwtFilter 제외");
        } else if (signupRequestMatcher.matches(request)) {
            log.info("회원가입 시도입니다.JwtFilter 제외");
        } else {
            String token = "";
            String email = "";
            String authorizationHeader = request.getHeader("authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            if (token != null && jwtUtils.validateToken(token)) {
                Authentication authentication = jwtUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
