package com.kavana.journalApp.service;

import com.kavana.journalApp.entity.User;
import com.kavana.journalApp.repository.UserRepository;
import com.kavana.journalApp.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @Disabled
    @Test
    public void findByUserNameTest(){
        assertNotNull( userRepository.findByUserName("Chethan"));
    }

    @ParameterizedTest
    @ValueSource(strings =
            {
                    "Chethan",
                    "Jaivik",
                    "Kavana2"
            }
    )
    public void getJournalEntriesForUserTest(String name){
        User user=userRepository.findByUserName("Chethan");
        assertFalse(user.getJournalEntryList().isEmpty());

    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void saveNewUserTest(User user){
        assertTrue(userService.saveNewUser(user));
    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "5,5,10",
            "10,10,20",
            "1,1,2"}
    )
    public void test(int a, int b, int expected){
        assertEquals(expected,a + b );
    }
}
