package com.paste_bin_clone.security.jwt;

import com.paste_bin_clone.entities.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private  Long validityTimeInMilliseconds;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected  void Init(){secret= Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String userName, List<com.paste_bin_clone.entities.Role> roles){
        Claims claims= Jwts.claims().setSubject(userName);
        claims.put("roles",getRoleNames(roles));

        Date now=new Date();
        Date validity=new Date(now.getTime()+validityTimeInMilliseconds);
        String date=validity.toString();
        return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(getUserNameByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserNameByToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken=req.getHeader("Authorization");
        return  bearerToken!=null && bearerToken.startsWith("Bearer_") ? bearerToken.substring(7,bearerToken.length()) : null ;

    }

    public boolean validateToken(String token) {
        try{
            Jws<Claims> claimsJws=Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return  new Date().before(claimsJws.getBody().getExpiration());
        }
        catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result=new ArrayList<>();
        userRoles.forEach(role->{
            result.add(role.getName());
        });
        return result;
    }
}