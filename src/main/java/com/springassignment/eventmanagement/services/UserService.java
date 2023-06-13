package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.entity.Users;

import java.util.List;

public interface UserService {
    public List<Users> findAll();

    public Users findById(int theId);

    public Users save(Users theUsers);

    public void deleteById(int theId);

    public Users update(int theId, Users theUsers);
}
