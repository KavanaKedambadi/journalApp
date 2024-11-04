package com.kavana.journalApp.service;

import com.kavana.journalApp.entity.User;
import com.kavana.journalApp.repository.UserRepository;
import com.kavana.journalApp.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

   @Disabled
   @Test public void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram2").password("asdaaddf").roles(new ArrayList<>()).build());
        UserDetails user=userDetailsServiceImpl.loadUserByUsername("Ram");
        Assertions.assertNotNull(user);
    }
}
