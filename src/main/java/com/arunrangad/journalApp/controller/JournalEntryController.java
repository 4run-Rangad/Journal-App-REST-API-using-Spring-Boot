package com.arunrangad.journalApp.controller;

import com.arunrangad.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new  ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{id}")
    public JournalEntry getEntryById(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @DeleteMapping("id/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id){
        return journalEntries.remove(id);
    }

    @PutMapping("id/{id}")
    public JournalEntry updateEntryById(@PathVariable Long id, @RequestBody JournalEntry myEntry){
        return  journalEntries.put(id, myEntry);
    }
}
