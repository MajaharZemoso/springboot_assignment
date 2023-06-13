package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.entity.Users;
import com.springassignment.eventmanagement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        List<Users> users = Arrays.asList(new Users(1, "User 1"), new Users(2, "User 2"));
        when(userService.findAll()).thenReturn(users);

        List<Users> result = userController.getAllUsers();

        assertEquals(users, result);
    }

    @Test
    void getUsersById_WithValidId_ReturnsUser() {
        // Arrange
        int userId = 1;
        Users user = new Users(userId, "User 1");
        when(userService.findById(userId)).thenReturn(user);

        ResponseEntity<Users> response = userController.getUsersById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getUsersById_WithInvalidId_ReturnsNotFound() {
        int userId = 1;
        when(userService.findById(userId)).thenThrow(NoSuchElementException.class);

        ResponseEntity<Users> response = userController.getUsersById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createUser_ReturnsCreatedUser() {

        Users user = new Users(1, "User 1");
        when(userService.save(user)).thenReturn(user);

        ResponseEntity<Users> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void updateUser_WithExistingUser_ReturnsUpdatedUser() {

        int userId = 1;
        Users existingUser = new Users(userId, "User 1");
        Users updatedUser = new Users(userId, "Updated User 1");
        when(userService.update(userId, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<Users> response = userController.updateUsers(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void updateUser_WithNonexistentUser_ReturnsNotFound() {

        int userId = 1;
        Users updatedUser = new Users(userId, "Updated User 1");
        when(userService.update(userId, updatedUser)).thenThrow(NoSuchElementException.class);


        ResponseEntity<Users> response = userController.updateUsers(userId, updatedUser);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
