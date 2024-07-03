package com.anuragsahu.journalApp.service;

import com.anuragsahu.journalApp.Repository.UserRepository;
import com.anuragsahu.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void SaveUser(User UserEntry){
        userRepository.save(UserEntry);
    }
    public void SaveNewEntry(User UserEntry){
        UserEntry.setPassword(passwordEncoder.encode(UserEntry.getPassword()));
        UserEntry.setRoles(List.of("USER"));
        userRepository.save(UserEntry);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByusername(username);
    }
}
