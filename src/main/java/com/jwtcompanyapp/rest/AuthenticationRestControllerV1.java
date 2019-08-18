package com.jwtcompanyapp.rest;

import com.jwtcompanyapp.dto.AuthenticationRequestDto;
import com.jwtcompanyapp.dto.RegistrationRequestDto;
import com.jwtcompanyapp.model.ActivationCode;
import com.jwtcompanyapp.model.User;
import com.jwtcompanyapp.security.jwt.JwtTokenProvider;
import com.jwtcompanyapp.service.ActivationCodeService;
import com.jwtcompanyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final ActivationCodeService activationCodeService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, ActivationCodeService activationCodeService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.activationCodeService = activationCodeService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("registration")
    public ResponseEntity registration(@RequestBody RegistrationRequestDto registrationRequestDto) {

        if (!registrationRequestDto.getPassword().equals(registrationRequestDto.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.register(registrationRequestDto.toUser());

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String password = activationCodeService.getPassword();

        activationCodeService.sendSms(user.getPhoneNumber(), password);

        ActivationCode activationCode = new ActivationCode();
        activationCode.setActivationCode(password);
        activationCode.setUserId(user.getId());

        activationCodeService.save(activationCode);

        return ResponseEntity.ok(registrationRequestDto);
    }

    @GetMapping(value = "activation_code/{user_id}/{password}")
    public ResponseEntity activationCodeAuth(
            @PathVariable(name = "user_id") Long id, @PathVariable(name = "password") String password) {
        ActivationCode activationCode = activationCodeService.findByUserId(id);

        if (activationCode == null || !activationCode.getActivationCode().equals(password)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        activationCodeService.update(activationCode);

        User user = userService.findById(id);
        userService.update(user);

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        String username = user.getUsername();

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);


        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "change_password/{user_id}")
    public ResponseEntity changePassword(
            @PathVariable(name = "user_id") Long id) {
        ActivationCode activationCode = activationCodeService.findByUserId(id);

        if (activationCode == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(id);

        String password = activationCodeService.getPassword();

        activationCodeService.sendSms(user.getPhoneNumber(), password);

        activationCode.setActivationCode(password);

        activationCodeService.update(activationCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "change_password/{user_id}/{activation_code}/{password}")
    public ResponseEntity sendActivationCodeForChangePassword(
            @PathVariable(name = "user_id") Long id, @PathVariable(name = "activation_code") String activation,
            @PathVariable(name = "password") String password) {
        ActivationCode activationCode = activationCodeService.findByUserId(id);

        if (activationCode == null || !activationCode.getActivationCode().equals(activation)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(id);

        user.setPassword(password);
        userService.save(user);

        activationCodeService.update(activationCode);

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        String username = user.getUsername();

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
