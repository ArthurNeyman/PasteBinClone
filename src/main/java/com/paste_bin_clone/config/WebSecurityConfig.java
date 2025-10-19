package com.paste_bin_clone.config;

import com.paste_bin_clone.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String USER_ENDPOINT = "/user/**";
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String PASTE_ENDPOINT = "/paste/**";

    private static final String[] SWAGGER_WHITELIST = {
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/api-docs/**",
        "/swagger-resources/**",
        "/swagger-resources",
        "/configuration/ui",
        "/configuration/security",
        "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(
                authz ->
                    authz
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(AUTH_ENDPOINT,PASTE_ENDPOINT).permitAll()
                        .requestMatchers(USER_ENDPOINT).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")

                        .anyRequest().authenticated())
            .sessionManagement(
                session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
