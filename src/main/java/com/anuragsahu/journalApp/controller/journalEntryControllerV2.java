package com.anuragsahu.journalApp.controller;

import com.anuragsahu.journalApp.entity.JournalEntity;
import com.anuragsahu.journalApp.entity.User;
import com.anuragsahu.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.anuragsahu.journalApp.service.journalEntryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class journalEntryControllerV2 {

    @Autowired
    private journalEntryService journalEntryService;
    @Autowired
    private UserService userService;
 @GetMapping
 public ResponseEntity<List<JournalEntity>> getAllJournalEntriesOfUser(){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();
     User userInDb = userService.findByUserName(username);
         List<JournalEntity> all = userInDb.getJournalEntries();
         if(all != null && !all.isEmpty()) {
             return new ResponseEntity<>(all, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 }
@PostMapping
    public ResponseEntity<JournalEntity> createEntity(@RequestBody JournalEntity myEntry) {
     try{
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
         User userInDb = userService.findByUserName(username);
         journalEntryService.SaveEntry(myEntry,username);
         return new ResponseEntity<JournalEntity>(myEntry,HttpStatus.CREATED);
     }
     catch (Exception e){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }

}
@GetMapping("/id/{myId}")
public ResponseEntity<JournalEntity> getJournalEntityById(@PathVariable ObjectId myId){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUserName(username);
    List<JournalEntity> collect = user.getJournalEntries().stream().filter(x->(x.getId()).equals(myId)).toList();
    if(!collect.isEmpty()) {
        JournalEntity entry = collect.get(0);
        return new ResponseEntity<JournalEntity>(entry, HttpStatus.OK);
    }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
@DeleteMapping("id/{myId}")
    public ResponseEntity<?> DeleteJournalEntityById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@PutMapping("/id/{myId}")
    public ResponseEntity<?> UpdateJournalEntityById(@PathVariable ObjectId myId,@RequestBody JournalEntity NewEntry) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUserName(username);
    List<JournalEntity> collect = user.getJournalEntries().stream().filter(x->(x.getId()).equals(myId)).toList();
    if(!collect.isEmpty()) {
        JournalEntity OldEntry = collect.get(0);
        if (OldEntry != null) {
            OldEntry.setTitle(NewEntry.getTitle() != null && (!NewEntry.getTitle().equals("")) ? NewEntry.getTitle() : OldEntry.getTitle());
            OldEntry.setContent(NewEntry.getContent() != null && (!NewEntry.getContent().equals("")) ? NewEntry.getContent() : OldEntry.getContent());
            journalEntryService.SaveEntry(OldEntry);
            return new ResponseEntity<>(OldEntry, HttpStatus.OK);
    }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

}
}
