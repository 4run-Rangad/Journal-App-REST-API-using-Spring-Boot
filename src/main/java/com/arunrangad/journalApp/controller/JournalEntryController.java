package com.arunrangad.journalApp.controller;

import com.arunrangad.journalApp.entity.JournalEntry;
import com.arunrangad.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{id}")
    public JournalEntry getEntryById(@PathVariable ObjectId id){
        return journalEntryService.getById(id).orElse(null);
    }

    @DeleteMapping("id/{id}")
    public boolean deleteEntryById(@PathVariable ObjectId id){
        journalEntryService.deleteById(id);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        if (old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return old;
        }
        return null;
    }
}
