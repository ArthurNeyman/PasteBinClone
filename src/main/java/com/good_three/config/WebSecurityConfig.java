package com.good_three.config;

import com.good_three.security.jwt.JwtConfigurer;
import com.good_three.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String USER_ENDPOINT = "/user/**";
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String PASTE_ENDPOINT = "/paste/**";

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                .antMatchers(USER_ENDPOINT).hasAuthority("USER")
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .antMatchers(PASTE_ENDPOINT).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}