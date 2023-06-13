package com.springassignment.eventmanagement.dao;

import com.springassignment.eventmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {
}
