package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.UserRepository;
import com.sapo.shipping.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
