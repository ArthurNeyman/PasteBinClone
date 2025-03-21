package com.paste_bin_clone.config;

import com.paste_bin_clone.security.jwt.JwtConfigurer;
import com.paste_bin_clone.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String USER_ENDPOINT = "/user/**";
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String PASTE_ENDPOINT = "/paste/**";
    private static final String SWAGGER_ENDPOINT = "/swagger-ui/**";

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
                .cors(Customizer.withDefaults())
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT, PASTE_ENDPOINT).permitAll()
                .antMatchers(USER_ENDPOINT).hasAuthority("USER")
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .apply(new JwtConfigurer(jwtTokenProvider))
                ;


    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v3/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers(SWAGGER_ENDPOINT);
    }

}