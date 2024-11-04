package com.kavana.journalApp.controller;

import com.kavana.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    public Map<Long, JournalEntry> journalEntries= new HashMap<>();
    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
      //  journalEntries.put(myEntry.getId(),myEntry);
        return true;
    }
    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId){
        return journalEntries.get(myId);
    }

    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable long myId){
        journalEntries.remove(myId);
        return true;
    }

  //  @PutMapping
    //public JournalEntry updateJournalEntryById()
}
