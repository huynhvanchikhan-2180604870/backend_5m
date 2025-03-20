package com.nextgen.backend.controllers;

import com.nextgen.backend.config.JwtConstant;
import com.nextgen.backend.dtos.CategoryDTO;
import com.nextgen.backend.entites.Role;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.exceptions.CategoryException;
import com.nextgen.backend.exceptions.UserException;
import com.nextgen.backend.repositories.RoleCustomRepository;
import com.nextgen.backend.repositories.RoleRepository;
import com.nextgen.backend.services.CategoryServiceInterface;
import com.nextgen.backend.services.UserServiceInterface;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v2/categories")
public class CategoryController {
    @Autowired
    private CategoryServiceInterface categoryServiceInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private RoleCustomRepository roleCustomRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping()
    public ResponseEntity<?> createdCategory(@RequestBody CategoryDTO categoryDTO, @RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws CategoryException, UserException {
        Map<String, String> handleError = new HashMap<>();
        try {
            User user = userServiceInterface.findUserProfileByJwt(jwt);
            List<String> roleNames = null;
            HashSet<Role> roles = new HashSet<>();
            roleNames = roleCustomRepository.getAllRoles(user);
            roleNames.forEach(roleName -> {
                Role role = roleRepository.findByName(roleName);
                roles.add(role);
            });
            if(user == null || !roles.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))){
                throw new CategoryException("Forbidden");
            }
            CategoryDTO save = categoryServiceInterface.addCategory(categoryDTO);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        }catch (CategoryException cx){
            handleError.put("message: " , cx.getMessage());
            handleError.put("status: ", "error");
            handleError.put("code: ", "401");
        }
        return new ResponseEntity<>(handleError,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable UUID id) throws CategoryException{
        CategoryDTO categoryDTO = new CategoryDTO();
        Map<String, String> handleException = new HashMap<>();
        try{
            categoryDTO = categoryServiceInterface.findCategoryById(id);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }catch (CategoryException ex){
            handleException.put("messege: ", ex.getMessage());
            handleException.put("status: ", "error");
            handleException.put("code: ", "404");
        }
        return new ResponseEntity<>(handleException, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAllCategories(){
        return new ResponseEntity<>(categoryServiceInterface.findAllCategories(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO dto) throws CategoryException{
        CategoryDTO categoryDTO = new CategoryDTO();
        Map<String, String> handleException = new HashMap<>();
        try{
            categoryDTO = categoryServiceInterface.updateCategory(id, dto);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }catch (CategoryException ex){
            handleException.put("messege: ", ex.getMessage());
            handleException.put("status: ", "error");
            handleException.put("code: ", "500");
        }
        return new ResponseEntity<>(handleException, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) throws CategoryException{
        Map<String, String> handleException = new HashMap<>();
        Map<String, String> reponse = new HashMap<>();
        try{
            categoryServiceInterface.deleteCategory(id);
            reponse.put("messege: ", "Deleted successful");
            reponse.put("status: ", "success");
            reponse.put("code: ", "200");
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        }catch (CategoryException ex){
            handleException.put("messege: ", ex.getMessage());
            handleException.put("status: ", "error");
            handleException.put("code: ", "404");
        }
        return new ResponseEntity<>(handleException, HttpStatus.OK);
    }
}
