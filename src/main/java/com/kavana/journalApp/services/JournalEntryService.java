package com.kavana.journalApp.services;

import com.kavana.journalApp.entity.JournalEntry;
import com.kavana.journalApp.entity.User;
import com.kavana.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


@Autowired
private JournalEntryRepository journalEntryRepository;

@Autowired
private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry newEntry, String userName){

        try {
            User user=userService.getUserByName(userName);
            if (user != null) {
                newEntry.setDate(LocalDateTime.now());
                JournalEntry journalEntry = journalEntryRepository.save(newEntry);
                user.getJournalEntryList().add(journalEntry);
                userService.saveUser(user);
            }
        }catch(Exception e){
            throw new RuntimeException("Error occurred during updating "+e);
        }
    }

    public void saveEntry(JournalEntry updatedEntry) {
        updatedEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(updatedEntry);
    }
    public List<JournalEntry> getAll(){

       return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id,String userName){
        try {
            User user = userService.getUserByName(userName);
            boolean removed = false;
            try {
                if (user != null) {
                    removed = user.getJournalEntryList().removeIf(x -> x.getId().equals(id));
                    if (removed) {
                        journalEntryRepository.deleteById(id);
                        userService.saveUser(user);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Error occurred during updating " + e);
            }

            return removed;
        }catch (Exception e){
            System.out.println("Error while deleteing user "+ e);
            throw new RuntimeException("Error while deleteing user "+ e);
        }

    }





}
