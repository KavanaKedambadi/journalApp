package com.kavana.journalApp.controller;

import com.kavana.journalApp.entity.JournalEntry;
import com.kavana.journalApp.entity.User;
import com.kavana.journalApp.services.JournalEntryService;
import com.kavana.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController2 {

@Autowired
private JournalEntryService journalEntryService;

@Autowired
private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntriesForUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.getUserByName(username);
        List<JournalEntry> all=user.getJournalEntryList();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
       try {
           Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
           String userName= authentication.getName();
           journalEntryService.saveEntry(myEntry,userName);
           return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
       }catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user=userService.getUserByName(userName);
        List<JournalEntry> collect= user.getJournalEntryList().stream().filter(x->x.getId().equals(myId)).toList();

        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);


            if (journalEntry.isPresent()) {
                return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
            }
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user=userService.getUserByName(userName);
        List<JournalEntry> collect= user.getJournalEntryList().stream().filter(x->x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
         boolean removed=journalEntryService.deleteEntryById(myId,userName);

         if(removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         else{
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry
                ){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
         User user=userService.getUserByName(username);
         List<JournalEntry> collect= user.getJournalEntryList().stream().filter(x->x.getId().equals(myId)).toList();
        JournalEntry old=journalEntryService.getEntryById(myId).orElse(null);;
        if(old!=null){
            old.setTitle(!newEntry.getTitle().isEmpty() && !newEntry.getTitle().isBlank()? newEntry.getTitle() : old.getTitle());
            old.setContent(!newEntry.getContent().isEmpty() && !newEntry.getContent().isBlank()? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }

     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }
}
