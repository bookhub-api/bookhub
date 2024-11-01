package com.application.jetbill.repository;


import com.application.jetbill.model.entity.Role;
import com.application.jetbill.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findNyName(ERole name);
}
