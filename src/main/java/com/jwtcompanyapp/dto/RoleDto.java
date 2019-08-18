package com.jwtcompanyapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwtcompanyapp.model.Role;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;
    private String name;

    public Role toRole(RoleDto roleDto) {
        Role role = new Role();

        role.setId(roleDto.getId());
        role.setName(roleDto.getName());

        return role;
    }

    public static RoleDto fromRole(Role role) {
        RoleDto roleDto = new RoleDto();

        roleDto.setId(role.getId());
        roleDto.setName(role.getName());

        return roleDto;
    }
}
