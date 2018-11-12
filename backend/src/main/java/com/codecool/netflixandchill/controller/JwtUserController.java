package com.codecool.netflixandchill.controller;

import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.service.UserService;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class JwtUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/join")
    public ResponseEntity join(@RequestBody Map<String, String> requestJson) {
        if (userService.checkIfEmailAlreadyExists(requestJson.get("email")) || userService.checkIfUserNameAlreadyExists("username")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("email or username already exists");
        } else {
            this.userService.addUser(requestJson.get("username"),
                    requestJson.get("email"),
                    bCryptPasswordEncoder.encode(requestJson.get("password")));
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

}
