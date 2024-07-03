package com.anuragsahu.journalApp.Repository;

import com.anuragsahu.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>{
    User findByusername(String username);

    void deleteByUsername(String username);
}
