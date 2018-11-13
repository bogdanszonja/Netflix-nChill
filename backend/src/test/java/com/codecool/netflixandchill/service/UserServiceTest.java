package com.codecool.netflixandchill.service;

import com.codecool.netflixandchill.model.User;
import com.codecool.netflixandchill.repository.EpisodeRepository;
import com.codecool.netflixandchill.repository.SeasonRepository;
import com.codecool.netflixandchill.repository.SeriesRepository;
import com.codecool.netflixandchill.repository.UserRepository;
import com.codecool.netflixandchill.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;


@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Configuration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private EpisodeRepository episodeRepository;

    @MockBean
    private SeasonRepository seasonRepository;

    @MockBean
    private SeriesRepository seriesRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User tivadar = new User("tivadar", "tivadar@tivadar.hu", "tivadar", null, null, null, new Date());

        Mockito.when(userRepository.findByUserName(tivadar.getUserName()))
                .thenReturn(tivadar);

        Mockito.when(userRepository.findUserByEmailAddress(tivadar.getEmailAddress()))
                .thenReturn(tivadar);
    }


    @Test
    public void testFindUserByUserNameWhenUserExists() {
        String userName = "tivadar";
        User found = userService.findByUsername(userName);

        assertThat(found.getUserName())
                .isEqualTo(userName);
    }

    @Test
    public void testCheckIfEmailAlreadyExists() {
        String email = "tivadar@tivadar.hu";
        boolean emailExists = userService.checkIfEmailAlreadyExists(email);

        assertTrue(emailExists);
    }

    @Test
    public void testCheckIfEmailAlreadyExistsWhenEmailDoesNotExist() {
        String email = "ödön@tivadar.hu";
        boolean emailExists = userService.checkIfEmailAlreadyExists(email);

        assertFalse(emailExists);
    }

    @Test
    public void testCheckIfUserNameAlreadyExists() {
        String userName = "tivadar";
        boolean userNameExists = userService.checkIfUserNameAlreadyExists(userName);

        assertTrue(userNameExists);
    }

    @Test
    public void testCheckIfUserNameAlreadyExistsWhenUserNameDoesNotExist() {
        String userName = "ödön";
        boolean userNameExists = userService.checkIfUserNameAlreadyExists(userName);

        assertFalse(userNameExists);
    }
}
