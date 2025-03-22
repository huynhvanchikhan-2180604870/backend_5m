package com.nextgen.backend.controllers;

import com.nextgen.backend.config.JwtConstant;
import com.nextgen.backend.dtos.mappers.UserDtoMapper;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.exceptions.UserException;
import com.nextgen.backend.repositories.RoleCustomRepository;
import com.nextgen.backend.repositories.RoleRepository;
import com.nextgen.backend.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v2/users")
public class UserController {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleCustomRepository roleCustomRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfileByJwt(@RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws UserException{
        Map<String, String> handleError = new HashMap<>();
        try{
            User user = userServiceInterface.findUserProfileByJwt(jwt);
            if(user == null){
                throw  new UserException("User not found");
            }
            return new ResponseEntity<>(UserDtoMapper.toDto(user), HttpStatus.OK);
        }catch (UserException ex){
            handleError.put("message: " , ex.getMessage());
            handleError.put("status: ", "error");
            handleError.put("code: ", "404");
        }
        return new ResponseEntity<>(handleError, HttpStatus.OK);
    }
}
