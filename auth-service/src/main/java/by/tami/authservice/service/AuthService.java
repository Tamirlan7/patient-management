package by.tami.authservice.service;

import by.tami.authservice.dto.LoginRequestDto;
import by.tami.authservice.model.User;
import by.tami.authservice.util.JwtUtil;
import org.springframework.aop.ClassFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService,  PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDto request) {
        return userService
                .findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(
                        request.getPassword(), u.getPassword()
                ))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
    }
}
