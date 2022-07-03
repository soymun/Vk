package com.example.vk.Security;


import com.example.vk.Entity.Role;
import com.example.vk.Service.Implaye.UserServiceImp;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private UserServiceImp userServiceImp;
    @Autowired
    public JWTTokenProvider(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long validityInMillisecond;

    @PostConstruct
    protected void init(){secret = Base64.getEncoder().encodeToString(secret.getBytes());}

    public String createToken(String userName, Role role){
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", role.getAuthority());

        Date date = new Date();
        Date validity = new Date(date.getTime() + validityInMillisecond);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetailsService = this.userServiceImp.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetailsService, "", userDetailsService.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new JWTAuthException("Token is invalid");
        }
    }
}
