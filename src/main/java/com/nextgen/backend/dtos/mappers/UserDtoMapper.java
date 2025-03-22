package com.nextgen.backend.dtos.mappers;

import com.nextgen.backend.dtos.RoleDTO;
import com.nextgen.backend.dtos.UserDTO;
import com.nextgen.backend.entites.Role;
import com.nextgen.backend.entites.User;

import java.util.HashSet;
import java.util.List;

public class UserDtoMapper {
    public static UserDTO toDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRoles(setRoleUserToRoleUserDTO(user));
        return dto;
    }

    public static HashSet<RoleDTO> setRoleUserToRoleUserDTO(User user) {
        HashSet<RoleDTO> roleDTOSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            RoleDTO roleDTO = RoleDtoMapper.toDto(role);
            roleDTOSet.add(roleDTO);
        }
        return roleDTOSet;
    }
}
