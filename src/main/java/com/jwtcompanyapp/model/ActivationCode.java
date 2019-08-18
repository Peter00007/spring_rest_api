package com.jwtcompanyapp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "activation_code")
@Data
public class ActivationCode extends BaseEntity{

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "activation_code")
    private String activationCode;
}
