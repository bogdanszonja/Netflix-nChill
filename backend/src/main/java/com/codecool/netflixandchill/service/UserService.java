package com.codecool.netflixandchill.service;

import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(String username, String email, String hashedPassword) {
        User user = User.builder()
                .userName(username)
                .emailAddress(email)
                .password(hashedPassword)
                .registrationDate(new Date())
                .build();
        this.userRepository.save(user);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUserName(username);
    }

    public boolean checkIfEmailAlreadyExists(String email) {
        return userRepository.findUserByEmailAddress(email) != null;
    }

    public boolean checkIfUserNameAlreadyExists(String userName) {
        return userRepository.findByUserName(userName) != null;
    }

}
