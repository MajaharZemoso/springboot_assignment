package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.dto.UsersDTO;
import com.springassignment.eventmanagement.entity.Users;
import com.springassignment.eventmanagement.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService theUserService, ModelMapper modelMapper) {
        userService = theUserService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public List<UsersDTO> getAllUsers() {
        List<Users> usersList = userService.findAll();
        return usersList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") Integer id) {
        try {
            Users users = userService.findById(id);
            UsersDTO usersDTO = convertToDto(users);
            return ResponseEntity.ok(usersDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        Users users = convertToEntity(usersDTO);
        Users createdUser = userService.save(users);
        UsersDTO createdUserDTO = convertToDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable("id") int id, @RequestBody UsersDTO usersDTO) {
        try {
            Users users = convertToEntity(usersDTO);
            Users updatedUser = userService.update(id, users);
            UsersDTO updatedUserDTO = convertToDto(updatedUser);
            return ResponseEntity.ok(updatedUserDTO);
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

    private UsersDTO convertToDto(Users users) {
        return modelMapper.map(users, UsersDTO.class);
    }

    private Users convertToEntity(UsersDTO usersDTO) {
        return modelMapper.map(usersDTO, Users.class);
    }
}
