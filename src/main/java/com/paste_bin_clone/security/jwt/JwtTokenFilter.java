package com.paste_bin_clone.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] PUBLIC_PATHS = {
        "/auth/**",
        "/paste/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/swagger-ui.html"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestPath = httpRequest.getServletPath();
        if (isPublicPath(requestPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token=jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        if(token!=null && jwtTokenProvider.validateToken(token)){
            Authentication authentication=jwtTokenProvider.getAuthentication(token);
            if(authentication!=null)
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (publicPath.endsWith("/**")) {
                String basePath = publicPath.substring(0, publicPath.length() - 3);
                if (path.startsWith(basePath)) {
                    return true;
                }
            } else if (publicPath.equals(path)) {
                return true;
            }
        }
        return false;
    }
}
