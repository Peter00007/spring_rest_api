package com.jwtcompanyapp.service;


import com.jwtcompanyapp.model.ActivationCode;

public interface ActivationCodeService {
    ActivationCode save(ActivationCode activationCode);

    ActivationCode update(ActivationCode activationCode);

    ActivationCode findByUserId(Long userId);

    void sendSms(String phoneNumber, String message);

    String getPassword();
}
