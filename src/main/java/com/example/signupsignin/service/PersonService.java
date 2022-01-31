package com.example.signupsignin.service;

import com.example.signupsignin.repository.PersonRespository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "username %s not found";

    private PersonRespository personRespository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRespository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser() {
    }
}
