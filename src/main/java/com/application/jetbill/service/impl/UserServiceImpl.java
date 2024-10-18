package com.application.jetbill.service.impl;


import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.model.entity.User;
import com.application.jetbill.repository.UserRepository;
import com.application.jetbill.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User reguisterUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ResourceNotFoundException("El usuario con ese email ya esta registrado");
        }
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
