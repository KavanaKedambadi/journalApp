package com.kavana.journalApp.repository;

import com.kavana.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    Void deleteByUserName(String Username);
}
