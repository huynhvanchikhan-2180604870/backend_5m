package com.nextgen.backend.dtos;

import lombok.Data;

import java.util.UUID;
@Data
public class RoleDTO {
    private UUID id;
    private String name;
}
