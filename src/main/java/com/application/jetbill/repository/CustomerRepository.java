package com.application.jetbill.repository;

import com.application.jetbill.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existByFirstNameAndLastName(String firstName,String lastName);
//buscar si existe por nombre y apellido excepto el usuario actual
    boolean existByFirstNameAndLastNameAndUserIdNot(String firstName,
                                                    String lastName,
                                                    Integer userId);


}
