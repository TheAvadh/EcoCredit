package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.dto.JwtAuthenticationResponse;
import com.group1.ecocredit.dto.SignInRequest;
import com.group1.ecocredit.dto.SignUpRequest;
import com.group1.ecocredit.enums.HttpMessage;
import com.group1.ecocredit.models.Address;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.AuthenticationService;
import com.group1.ecocredit.services.ConfirmationTokenService;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.JWTService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public JwtAuthenticationResponse signup(SignUpRequest signUpRequest) {

        User user=new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRole(Role.USER);
        //  encrypt raw password to hash password
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Address userAddress = new Address();
        SignUpRequest.AddressBD addressBD = signUpRequest.getAddress();

        userAddress.setStreet(addressBD.getStreet());
        userAddress.setCity(addressBD.getCity());
        userAddress.setProvince(addressBD.getProvince());
        userAddress.setPostalCode(addressBD.getPostalCode());

        user.setAddress(userAddress);
        try{
            var token = confirmationTokenService.generateConfirmationToken(user.getId());
            confirmationTokenService.saveConfirmationToken(token, user);
            emailService.sendVerifyAccountEmail(user.getEmail(), token);
        }
        catch (MessagingException e){
            logger.error("An error occurred while sending the verification email", e);
        }


        user = userService.save(user);

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRole(user.getRole());

        return jwtAuthenticationResponse;

    }
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                    signInRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            throw e;
        }
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        var user= userService.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password."));
        var jwt=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(new HashMap<>(),user);

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setHttpMessage(HttpMessage.SUCCESS);
        jwtAuthenticationResponse.setRole(user.getRole());
        jwtAuthenticationResponse.setUserId(user.getId());

        return jwtAuthenticationResponse;
    }
}
