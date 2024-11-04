package com.kavana.journalApp.services;

import com.kavana.journalApp.entity.User;
import com.kavana.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    private static final Logger logger= LoggerFactory.getLogger(UserService.class);
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public boolean saveNewUser(User user){
        try {
            log.info("enter the method to initiate saving of new user {}",user.getUserName());
            log.debug("enter the method to initiate saving of new user {}",user.getUserName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.debug("encoded password is set for  new user {}",user.getUserName());
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.info("runtimeException has occure for {} " ,user.getUserName(), e);
            throw new RuntimeException(e);

        }

    }

    public void saveUser(User user){

        userRepository.save(user);
    }
    public List<User> getAll(){

        return userRepository.findAll();
    }

    public Optional<User> getEntryById(ObjectId id){
        return userRepository.findById(id);
    }

    public boolean deleteEntryById(ObjectId id){

        userRepository.deleteById(id);
        return true;

    }

    public User getUserByName(String userName){

      return   userRepository.findByUserName(userName);
    }


}
