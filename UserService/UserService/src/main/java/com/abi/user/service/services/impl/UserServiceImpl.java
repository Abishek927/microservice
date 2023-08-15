package com.abi.user.service.services.impl;

import com.abi.user.service.entities.User;
import com.abi.user.service.exceptions.ResourceNotFoundException;
import com.abi.user.service.repo.UserRepo;
import com.abi.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        String randomUserId= UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        userRepo.save(user);

        return user;
    }

    @Override
    public List<User> getAllUser() {
       return userRepo.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with the given id not found "+userId));

    }
}
