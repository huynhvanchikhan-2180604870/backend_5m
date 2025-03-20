package com.nextgen.backend.controllers;

import com.nextgen.backend.config.JwtConstant;
import com.nextgen.backend.dtos.ProductDTO;
import com.nextgen.backend.entites.Role;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.exceptions.CategoryException;
import com.nextgen.backend.exceptions.ProductException;
import com.nextgen.backend.exceptions.UserException;
import com.nextgen.backend.repositories.RoleCustomRepository;
import com.nextgen.backend.repositories.RoleRepository;
import com.nextgen.backend.services.ProductServiceInterface;
import com.nextgen.backend.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v2/products")
public class ProductController {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleCustomRepository roleCustomRepository;
    @Autowired
    private ProductServiceInterface productServiceInterface;

    @GetMapping()
    public ResponseEntity<?> findAllProduct(){
        return new ResponseEntity<>(productServiceInterface.findAllProduct(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable UUID id)throws ProductException{
        Map<String, String> handleError = new HashMap<>();
        try {
            return new ResponseEntity<>(productServiceInterface.findProductById(id), HttpStatus.OK);
        }catch (ProductException px){
            handleError.put("message", px.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "404");
        }
        return new ResponseEntity<>(handleError, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createNewProduct(@RequestHeader(JwtConstant.JWT_HEADER) String jwt, @RequestBody ProductDTO request) throws ProductException, CategoryException {
        Map<String, String> handleError = new HashMap<>();
        try {
            return new ResponseEntity<>(productServiceInterface.createNewProduct(request), HttpStatus.CREATED);
        }catch (CategoryException cx){
            handleError.put("message", cx.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "404");
        }
        return new ResponseEntity<>(handleError, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id ,@RequestHeader(JwtConstant.JWT_HEADER) String jwt, @RequestBody ProductDTO request) throws CategoryException, ProductException, UserException {
        Map<String, String> handleError = new HashMap<>();
        try {
            User user = userServiceInterface.findUserProfileByJwt(jwt);
            HashSet<Role> roles = new HashSet<>();
            List<String> roleNames = roleCustomRepository.getAllRoles(user);
            roleNames.forEach(roleName -> {
                Role role = roleRepository.findByName(roleName);
                roles.add(role);
            });


            if (user == null || !roles.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
                throw new ProductException("Forbidden");
            }
            return new ResponseEntity<>(productServiceInterface.updateProduct(request, id), HttpStatus.OK);
        }catch (ProductException px){
            handleError.put("message", px.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "404");
        }catch (CategoryException cx){
            handleError.put("message", cx.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "404");
        }catch (UserException ux){
            handleError.put("message", ux.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "401"); // Unauthorized
        }
        return new ResponseEntity<>(handleError, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable UUID id, @RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws ProductException, UserException{
        Map<String, String> handleError = new HashMap<>();
        Map<String, String> response = new HashMap<>();
        try {
            User user = userServiceInterface.findUserProfileByJwt(jwt);
            HashSet<Role> roles = new HashSet<>();
            List<String> roleNames = roleCustomRepository.getAllRoles(user);
            roleNames.forEach(roleName -> {
                Role role = roleRepository.findByName(roleName);
                roles.add(role);
            });


            if (user == null || !roles.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
                throw new ProductException("Forbidden");
            }
            productServiceInterface.deleteProduct(id);
            response.put("message", "Deleted successfully");
            response.put("status", "success");
            response.put("code", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ProductException px){
            handleError.put("message", px.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "404");
        }catch (CategoryException cx){
            handleError.put("message", cx.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "404");
        }catch (UserException ux){
            handleError.put("message", ux.getMessage());
            handleError.put("status", "error");
            handleError.put("code", "401"); // Unauthorized
        }
        return new ResponseEntity<>(handleError, HttpStatus.BAD_REQUEST);
    }
}
