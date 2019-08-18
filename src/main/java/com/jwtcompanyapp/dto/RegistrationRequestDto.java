package com.jwtcompanyapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwtcompanyapp.model.User;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private Date created;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setCreated(created);
        return user;
    }
}
