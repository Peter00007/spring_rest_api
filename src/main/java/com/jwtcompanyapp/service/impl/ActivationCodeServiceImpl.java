package com.jwtcompanyapp.service.impl;


import com.jwtcompanyapp.model.ActivationCode;
import com.jwtcompanyapp.model.Status;
import com.jwtcompanyapp.repository.ActivationCodeRepository;
import com.jwtcompanyapp.service.ActivationCodeService;
import com.jwtcompanyapp.twilio.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivationCodeServiceImpl implements ActivationCodeService {

    private final ActivationCodeRepository activationCodeRepository;

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public ActivationCodeServiceImpl(ActivationCodeRepository activationCodeRepository, TwilioConfiguration twilioConfiguration) {
        this.activationCodeRepository = activationCodeRepository;
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public ActivationCode save(ActivationCode activationCode) {
        activationCode.setCreated(new Date());
        activationCode.setUpdated(new Date());
        activationCode.setStatus(Status.ACTIVE);

        ActivationCode save = activationCodeRepository.save(activationCode);

        log.info("Saved activation code with id {}", activationCode.getId());

        return save;
    }

    @Override
    public ActivationCode update(ActivationCode activationCode) {
        activationCode.setUpdated(new Date());

        ActivationCode update = activationCodeRepository.save(activationCode);

        log.info("Updated activation code with id {}", activationCode.getId());

        return update;
    }

    @Override
    public ActivationCode findByUserId(Long userId) {
        ActivationCode activationCode = activationCodeRepository.findByUserId(userId);

        log.info("Success found by User id{}", userId);
        return activationCode;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        MessageCreator creator = Message.creator(to, from, message);
        creator.create();
        log.info("Send sms {" + message + "} to phoneNumber{" + phoneNumber + "}");
    }

    @Override
    public String getPassword() {
        String password = new Random().ints(6, 48, 57).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
        return password;
    }

}
