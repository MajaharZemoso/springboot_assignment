package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.UserRepository;
import com.springassignment.eventmanagement.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private UserRepository userRepository;

    @Autowired
    public UserServices(UserRepository theUserRepository) {
        this.userRepository = theUserRepository;
    }


    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }


    public Optional<Users> findUsersById(int theId) {
        return userRepository.findById(theId);

    }

    public Users saveUser(Users theUsers) {
        userRepository.save(theUsers);
        return theUsers;
    }

    public void deleteUserById(int theId) {
        userRepository.deleteById(theId);
    }

    public Users updateUser(int theId, Users updatedEvent) {
        Optional<Users> result = userRepository.findById(theId);

        if (result.isPresent()) {
            Users theEvent = result.get();
            theEvent.setFirstName(updatedEvent.getFirstName());
            theEvent.setLastName(updatedEvent.getLastName());
            theEvent.setAge(updatedEvent.getAge());
            theEvent.setGender(updatedEvent.getGender());
            userRepository.save(theEvent);
            return theEvent;
        } else {
            throw new RuntimeException("Did not find Event id : " + theId);
        }
    }
}
