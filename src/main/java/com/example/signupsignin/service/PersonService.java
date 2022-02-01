package com.example.signupsignin.service;

import com.example.signupsignin.model.Person;
import com.example.signupsignin.repository.PersonRespository;
import com.example.signupsignin.security.PasswordEncoder;
import com.example.signupsignin.token.ConfirmationToken;
import com.example.signupsignin.token.ConfirmationTokenRepository;
import com.example.signupsignin.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "username %s not found";
    private final static String USER_EXIST = "username %s already exist";

    private PersonRespository personRespository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRespository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }


    public String signUpUser(Person person) {
        String response = String.format(USER_EXIST, person.getEmail());

        boolean userExist = personRespository.findByEmail(person.getEmail()).isPresent();

        if(userExist) {
            throw new IllegalStateException(response);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        personRespository.save(person);


        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                person
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

    //  TODO: Send Email

        return token;
    }

}
