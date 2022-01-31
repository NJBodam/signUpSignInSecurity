package com.example.signupsignin.service;

import com.example.signupsignin.dto.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpService {
    private final PersonService personService;

    public String signUp(SignUpRequest request) {
        return personService.signUpUser(

        );
    }
}
