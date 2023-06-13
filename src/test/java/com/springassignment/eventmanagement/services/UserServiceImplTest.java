package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.UserRepository;
import com.springassignment.eventmanagement.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {

        List<Users> usersList = new ArrayList<>();
        usersList.add(new Users(1,"John", "Doe", 25, "Male"));
        usersList.add(new Users(2,"Jane", "Smith", 30, "Female"));

        when(userRepository.findAll()).thenReturn(usersList);


        List<Users> result = userService.findAll();

        assertEquals(usersList, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById_existingId_shouldReturnUser() {

        Users user = new Users(1, "John", "Doe", 25, "Male");

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        Users result = userService.findById(1);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {

        Users user = new Users(1, "John", "Doe", 25, "Male");

        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users result = userService.save(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteById() {

        userService.deleteById(1);
        verify(userRepository, times(1)).deleteById(1);
    }
}
