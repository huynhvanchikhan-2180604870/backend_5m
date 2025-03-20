package com.nextgen.backend.services;

import com.nextgen.backend.config.JwtProvider;
import com.nextgen.backend.entites.Role;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.exceptions.UserException;
import com.nextgen.backend.repositories.RoleCustomRepository;
import com.nextgen.backend.repositories.RoleRepository;
import com.nextgen.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImplement implements UserServiceInterface{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleCustomRepository roleCustomRepository;

    public User findUserById(UUID id){
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmailWithRole(email);
        List<String> roleNames = null;
        HashSet<Role> roles = new HashSet<>();
        if(user == null){
            throw new UserException("User is null");
        }
        roleNames = roleCustomRepository.getAllRoles(user);
        roleNames.forEach(roleName -> {
            Role role = roleRepository.findByName(roleName);
            roles.add(role);
        });

        user.setRoles(roles);
        return user;
    }

    @Override
    public User updateUser(User user) throws UserException {
        User newUser = findUserById(user.getId());
        if(user.getFullName() != null && user.getFullName() != newUser.getFullName()){
            newUser.setFullName(user.getFullName());
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User addUser(User user) throws UserException {
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            throw new UserException("Role not found");
        }

        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.getRoles().add(role);
        newUser.setFullName(user.getFullName());
        return userRepository.save(newUser);
    }
}
