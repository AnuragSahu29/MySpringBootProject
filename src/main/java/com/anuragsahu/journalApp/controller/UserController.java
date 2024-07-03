package com.anuragsahu.journalApp.controller;

import com.anuragsahu.journalApp.Repository.UserRepository;
import com.anuragsahu.journalApp.entity.JournalEntity;
import com.anuragsahu.journalApp.entity.User;
import com.anuragsahu.journalApp.service.UserService;
import com.anuragsahu.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
// @GetMapping
// public List<User> getAllUsers(){
//     return userService.getAll();
// }

@DeleteMapping("/user")
public ResponseEntity<?> deleteUserById(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    userRepository.deleteByUsername(username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

 @PutMapping
   public ResponseEntity<?> UpdateUser(@RequestBody User user){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();
     User userInDb = userService.findByUserName(username);
     if(userInDb != null){
         userInDb.setUsername(user.getUsername());
         userInDb.setPassword(user.getPassword());
         userService.SaveUser(user);
     }
     return new ResponseEntity<>(HttpStatus.OK);
 }

}
