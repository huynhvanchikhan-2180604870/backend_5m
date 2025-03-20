package com.nextgen.backend.controllers;

import com.nextgen.backend.config.JwtProvider;
import com.nextgen.backend.dtos.RoleDTO;
import com.nextgen.backend.entites.Role;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.exceptions.UserException;
import com.nextgen.backend.repositories.RoleRepository;
import com.nextgen.backend.repositories.UserRepository;
import com.nextgen.backend.request.AuthRequest;
import com.nextgen.backend.request.UserRequest;
import com.nextgen.backend.response.AuthenticationResponse;
import com.nextgen.backend.services.CustomUserDetailsServiceImplementation;
import com.nextgen.backend.services.UserServiceImplement;
import com.nextgen.backend.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetailsService;

    @PostMapping("/roles")
    public ResponseEntity<?> createNewRole(@RequestBody RoleDTO role){
        Role add = new Role();
        add.setId(UUID.randomUUID());
        add.setName(role.getName());
        return new ResponseEntity<>(roleRepository.save(add), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        User userLogin = userRepository.findByEmail(request.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);
        AuthenticationResponse response = new AuthenticationResponse(token, true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("register")
    public ResponseEntity<?> createNewUser(@RequestBody UserRequest request) throws UserException{
        User isEmailExist = userRepository.findByEmail(request.email);
        Map<String, String> handleError = new HashMap<>();
        try{
            if(isEmailExist != null){
                throw new UserException("Email Already Exists");
            }
            User createUser = new User();
            createUser.setId(UUID.randomUUID());
            createUser.setEmail(request.email);
            createUser.setPassword(request.password);
            createUser.setFullName(request.fullname);
            User save = userServiceInterface.addUser(createUser);
            Authentication auth = new UsernamePasswordAuthenticationToken(save.getEmail(), save.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtProvider.generateToken(auth);
            AuthenticationResponse response = new AuthenticationResponse(token, true);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (UserException ex){
            handleError.put("message: " , ex.getMessage());
            handleError.put("status: ", "error");
            handleError.put("code: ", "404");
        }
        return new ResponseEntity<>(handleError,HttpStatus.OK);
    }
}
