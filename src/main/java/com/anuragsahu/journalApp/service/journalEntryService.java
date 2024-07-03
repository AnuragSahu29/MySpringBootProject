package com.anuragsahu.journalApp.service;

import com.anuragsahu.journalApp.Repository.JournalEntryRepository;
import com.anuragsahu.journalApp.entity.JournalEntity;
import com.anuragsahu.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
@Transactional
    public void SaveEntry(JournalEntity journalEntry, String username){
        User user = userService.findByUserName(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntity saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.SaveUser(user);
    }
    public void SaveEntry(JournalEntity journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntity> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public void deleteById(ObjectId id, String username){
    try {
        User user = userService.findByUserName(username);
        boolean bool = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (bool) {
            userService.SaveUser(user);
            journalEntryRepository.deleteById(id);
        }
    }
    catch (Exception e){
        System.out.println(e);
        throw new RuntimeException("An error occurred in delete call");
    }

    }

}
