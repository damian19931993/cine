package com.cine.cine.service;

import com.cine.cine.repository.UserRepository;
import com.cine.cine.entity.CinemaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CinemaUser save(CinemaUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public CinemaUser findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public CinemaUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
