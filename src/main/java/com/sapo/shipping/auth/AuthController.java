package com.sapo.shipping.auth;


import com.sapo.shipping.response.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public GeneralResponse<?> register(
            @RequestBody RegisterRequest request
    ) {
        try{
            return GeneralResponse.ok("success", "Successfully fetched",service.register(request));
        }catch (Exception e) {
            return GeneralResponse.failed("failed", e.getMessage());
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }


}
