package com.arunrangad.journalApp.controller;

import com.arunrangad.journalApp.entity.User;
import com.arunrangad.journalApp.repository.UserRepository;
import com.arunrangad.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user){
//        try {
//            userService.saveEntry(user);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping("id/{id}")
//    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id){
//        Optional<User> user = userService.getById(id);
//        if (user.isPresent()){
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
    @DeleteMapping
    public ResponseEntity<?> deleteEntryById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userIndb = userService.findByUserName(userName);
        userIndb.setUserName(user.getUserName());
        userIndb.setPassword(user.getPassword());
        userService.saveEntry(userIndb);
        return new ResponseEntity<>(userIndb, HttpStatus.OK);
    }
}
