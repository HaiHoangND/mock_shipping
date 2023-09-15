package com.sapo.shipping.auth;

import com.sapo.shipping.config.JwtService;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new BusinessException("400", "error", "Email đã tồn tại");
        }
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .address(request.getAddress())
                .phone(request.getPhone())
                .gender(request.getGender())
                .workingStatus(true)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var res =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .type("success")
                    .message("Đăng nhập thành công")
                    .id(user.getId())
                    .accessToken(jwtToken)
                    .userName(user.getFullName())
                    .profilePicture(user.getProfilePicture())
                    .role(user.getRole())
                    .build();
        } catch (BadCredentialsException e) {
            System.out.println(e);
            var user = repository.findByEmail(request.getEmail());
            if (!user.isPresent()) {
                // Tên người dùng không tồn tại
                return AuthenticationResponse.builder()
                        .type("failed")
                        .message("Email không tồn tại")
                        .build();
            } else {
                // Mật khẩu không chính xác
                return AuthenticationResponse.builder()
                        .type("failed")
                        .message("Sai mật khẩu")
                        .build();
            }
        }

    }

}
