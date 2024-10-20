package com.application.jetbill.repository;

import com.application.jetbill.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
}