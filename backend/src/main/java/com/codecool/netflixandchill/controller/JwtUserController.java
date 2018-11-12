package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class JwtUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/join")
    public void join(@RequestBody Map<String, String> requestJson) {
        this.userService.addUser(requestJson.get("username"),
                requestJson.get("email"),
                bCryptPasswordEncoder.encode(requestJson.get("password")));
    }

    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

}
