package com.nextgen.backend.request;

import com.nextgen.backend.entites.Role;

import java.util.HashSet;
import java.util.Set;

public class UserRequest {
    public String email;



    public String password;

    public String phone_number;
    public String fullname;



    public Set<Role> roles = new HashSet<>();
}
