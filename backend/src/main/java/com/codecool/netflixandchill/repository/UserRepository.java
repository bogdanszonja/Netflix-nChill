package com.codecool.netflixandchill.repository;

import com.codecool.netflixandchill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    User findUserByEmailAddress(String email);

}
