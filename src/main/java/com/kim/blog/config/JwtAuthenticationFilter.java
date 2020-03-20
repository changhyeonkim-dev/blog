package com.kim.blog.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean { //UsernamePasswordAuthenticationFilter앞에 세팅할 필터
    private final JwtTokenProvider jwtTokenProvider;
    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request); //header에서 토큰 파싱
        if(token == null) {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            if(cookies!=null){ // cookie에서 토큰 파싱
                token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("X-AUTH-TOKEN")).findFirst().map(Cookie::getValue).orElse(null);
            }
        }
        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰 유효기간확인
            Authentication auth = jwtTokenProvider.getAuthentication(token); //토큰으로 인증정보 확인
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
