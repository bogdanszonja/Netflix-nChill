package com.codecool.netflixandchill;

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

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

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
        User tivadar = new User("tivadar", "tivadar", "tivadar", null, null, null, new Date());

        Mockito.when(userRepository.findByUserName(tivadar.getUserName()))
                .thenReturn(tivadar);
    }


    @Test
    public void testFindUserByUserNameWhenUserExists() {
        String userName = "tivadar";
        User found = userService.findByUsername(userName);

        assertThat(found.getUserName())
                .isEqualTo(userName);
    }
}
