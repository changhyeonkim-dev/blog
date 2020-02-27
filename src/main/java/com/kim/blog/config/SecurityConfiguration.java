package com.kim.blog.config;

import com.kim.blog.account.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AccountDetailService accountDetailService;
    private final AccountPasswordEncoder accountPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .permitAll()
                .successForwardUrl("/sign-in")
                .and()
                .authorizeRequests()
                    .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/write-form").hasAuthority(Role.ADMIN.name())
                    .antMatchers(HttpMethod.POST).hasAuthority(Role.ADMIN.name())
                    .antMatchers(HttpMethod.PUT).hasAuthority(Role.ADMIN.name())
                    .antMatchers(HttpMethod.DELETE).hasAuthority(Role.ADMIN.name())
                        .anyRequest().permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailService)
                .passwordEncoder(accountPasswordEncoder);
    }
}
