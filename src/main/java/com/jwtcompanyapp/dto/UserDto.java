package com.jwtcompanyapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwtcompanyapp.model.Role;
import com.jwtcompanyapp.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date created;
    private String phoneNumber;
    private String password;
    private List<RoleDto> roles;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCreated(created);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);

        List<Role> list = new ArrayList<>();

        RoleDto roleDto = new RoleDto();

        for (RoleDto getRoles : roles) {
            list.add(roleDto.toRole(getRoles));
        }

        user.setRoles(list);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCreated(user.getCreated());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());

        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : user.getRoles()) {
            roleDtoList.add(RoleDto.fromRole(role));
        }

        userDto.setRoles(roleDtoList);

        return userDto;
    }
}
