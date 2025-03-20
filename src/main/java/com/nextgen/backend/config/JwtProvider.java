package com.nextgen.backend.config;

import com.nextgen.backend.entites.Role;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.repositories.RoleCustomRepository;
import com.nextgen.backend.repositories.RoleRepository;
import com.nextgen.backend.repositories.UserRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
@Service
public class JwtProvider {
    @Autowired
    private UserRepository userRepository;
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET.getBytes());

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleCustomRepository roleCustomRepository;

    public String generateToken(Authentication authentication){
        String emailreq = (String) authentication.getPrincipal();
        User user = userRepository.findByEmail(emailreq);

        List<String> roles = null;
        List<Role> roleList = new ArrayList<>();
        if(user != null){
            roles = roleCustomRepository.getAllRoles(user);
            roles.forEach( role -> {
                Role r = roleRepository.findByName(role);
                roleList.add(r);
            });
        }

        List<String> roleOfUser = roleList.stream().map(Role::getName).collect(Collectors.toList());
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", authentication.getName())
                .claim("roles", roleOfUser)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromToken(String token){
        token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
