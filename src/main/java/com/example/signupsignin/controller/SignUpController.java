package com.example.signupsignin.controller;

import com.example.signupsignin.dto.SignUpRequest;
import com.example.signupsignin.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/signup")
@AllArgsConstructor
public class SignUpController {

    private SignUpService signUpService;

    @PostMapping
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }
}
