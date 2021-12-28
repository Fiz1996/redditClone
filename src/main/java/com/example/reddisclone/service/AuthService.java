package com.example.reddisclone.service;

import com.example.reddisclone.dto.AuthenticationResponse;
import com.example.reddisclone.dto.LoginRequest;
import com.example.reddisclone.dto.RegisterRequest;
import com.example.reddisclone.entity.NotificationEmail;
import com.example.reddisclone.entity.Users;
import com.example.reddisclone.entity.VerificationToken;
import com.example.reddisclone.exception.RedditCloneExcption;
import com.example.reddisclone.repository.UserRepository;
import com.example.reddisclone.repository.VerificationTokenRepository;
import com.example.reddisclone.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
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

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new RedditCloneExcption("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }
//    @Transactional
   private void fetchUserAndEnable(VerificationToken verificationToken) {
        @NotBlank(message = "Username is required")
        String username = verificationToken.getUsers().getUsername();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new RedditCloneExcption("User not found with name " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token,loginRequest.getUsername());
    }
}
