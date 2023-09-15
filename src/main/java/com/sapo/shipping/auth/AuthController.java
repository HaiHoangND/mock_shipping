package com.sapo.shipping.auth;


import com.sapo.shipping.entity.User;
import com.sapo.shipping.repository.UserRepository;
import com.sapo.shipping.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private final UserRepository repository;
    private final AuthService service;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository repository, AuthService service, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public GeneralResponse<?> register(
            @RequestBody RegisterRequest request
    ) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            return GeneralResponse.failed("failed",
                    "Tài khoản đã tồn tại");

        }
        return GeneralResponse.ok("success",
                "Successfully fetched",service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
