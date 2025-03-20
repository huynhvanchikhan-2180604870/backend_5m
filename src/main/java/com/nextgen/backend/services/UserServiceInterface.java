package com.nextgen.backend.services;

import com.nextgen.backend.entites.User;
import com.nextgen.backend.exceptions.UserException;

import java.util.UUID;

public interface UserServiceInterface {
    public User findUserById(UUID id)throws UserException;
    public User findUserProfileByJwt(String jwt)throws UserException;
    public User updateUser(User user)throws UserException;
    public User addUser(User user)throws UserException;
}
