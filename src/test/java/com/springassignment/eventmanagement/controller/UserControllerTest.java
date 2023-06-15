package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.dto.UsersDTO;
import com.springassignment.eventmanagement.entity.Users;
import com.springassignment.eventmanagement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUserById_WithValidId_ReturnsUserDTO() {
        int userId = 1;
        Users user = new Users(userId, "User 1");
        UsersDTO userDTO = new UsersDTO(userId, "User 1");
        when(userService.findById(userId)).thenReturn(user);
        when(modelMapper.map(user, UsersDTO.class)).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void getUserById_WithInvalidId_ReturnsNotFound() {
        int userId = 1;
        when(userService.findById(userId)).thenThrow(NoSuchElementException.class);

        ResponseEntity<UsersDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createUser_ReturnsCreatedUserDTO() {
        UsersDTO userDTO = new UsersDTO(1, "User 1");
        Users user = new Users(1, "User 1");
        when(modelMapper.map(userDTO, Users.class)).thenReturn(user);
        when(userService.save(user)).thenReturn(user);
        when(modelMapper.map(user, UsersDTO.class)).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void deleteUser_WithExistingUser_ReturnsNoContent() {
        int userId = 1;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_WithNonexistentUser_ReturnsNotFound() {
        int userId = 1;
        doThrow(NoSuchElementException.class).when(userService).deleteById(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
