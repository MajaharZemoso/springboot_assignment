package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.entity.Users;
import com.springassignment.eventmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable("id") Integer id) {
        try {
            Users users = userService.findById(id);
            return ResponseEntity.ok(users);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users users1 = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(users1);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUsers(@PathVariable("id") int id, @RequestBody Users users) {
        try {
            Users theusers = userService.update(id,users);
            return ResponseEntity.ok(theusers);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
