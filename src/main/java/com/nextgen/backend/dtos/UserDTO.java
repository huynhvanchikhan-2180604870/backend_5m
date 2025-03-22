package com.nextgen.backend.dtos;

import com.nextgen.backend.entites.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String fullName;
    private Set<RoleDTO> roles = new HashSet<>();
}
