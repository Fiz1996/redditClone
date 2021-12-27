package com.example.reddisclone.service;

import com.example.reddisclone.dto.RegisterRequest;
import com.example.reddisclone.entity.NotificationEmail;
import com.example.reddisclone.entity.Users;
import com.example.reddisclone.entity.VerificationToken;
import com.example.reddisclone.repository.UserRepository;
import com.example.reddisclone.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
    }
//    @Transactional
    public void signup(RegisterRequest registerRequest) {
        Users users = new Users();
        users.setUsername(registerRequest.getUsername());
        users.setEmail(registerRequest.getEmail());
        users.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        users.setCreated(Instant.now());
        users.setEnabled(false);
        userRepository.save(users);
        String token = generateVerificationToken(users);
        mailService.sendMail(new NotificationEmail("Please Activate your email",
                users.getEmail(), "Thank you for signing up to redit "+token));
    }

    private String generateVerificationToken(Users users) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUsers(users);
        verificationTokenRepository.save(verificationToken);
        return token;

    }
}
