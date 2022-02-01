package com.example.signupsignin.service;

import com.example.signupsignin.Email.EmailValidator;
import com.example.signupsignin.dto.SignUpRequest;
import com.example.signupsignin.model.Person;
import com.example.signupsignin.model.PersonRole;
import com.example.signupsignin.token.ConfirmationToken;
import com.example.signupsignin.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SignUpService {

    private EmailValidator emailValidator;
    private PersonService personService;
    private ConfirmationTokenService confirmationTokenService;

    public String signUp(SignUpRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
             throw new IllegalStateException("email not valid"); // find better ways of handling exceptions
        }
        return personService.signUpUser(
                new Person(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        PersonRole.USER
                )
        );
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("token already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService(token);

    }

}

// @EnableGlobalSecurityCon... find out
