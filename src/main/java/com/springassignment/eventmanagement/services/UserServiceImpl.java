package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.UserRepository;
import com.springassignment.eventmanagement.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository) {
        this.userRepository = theUserRepository;
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(int theId) {
        Optional<Users> result =  userRepository.findById(theId);

        Users theUsers = null;

        if(result.isPresent()){
            theUsers = result.get();
        }
        else{
            throw new RuntimeException("Did not find User id : "+theId);
        }
        return theUsers;
    }

    @Override
    public Users save(Users theUsers) {
        userRepository.save(theUsers);
        return theUsers;
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }

    @Override
    public Users update(int theId, Users updatedEvent) {
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
