package com.nextgen.backend.dtos.mappers;

import com.nextgen.backend.dtos.RoleDTO;
import com.nextgen.backend.entites.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDtoMapper {
    public static RoleDTO toDto(Role role){
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public static List<RoleDTO> toDtos(List<Role> roles){
        List<RoleDTO> dtos = new ArrayList<>();
        roles.forEach(role ->{
            RoleDTO dto = toDto(role);
            dtos.add(dto);
        });

        return dtos;
    }
}
