package com.application.jetbill.repository;

import com.application.jetbill.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByFirstNameAndLastName(String firstName,String lastName);
//buscar si existe por nombre y apellido excepto el usuario actual
    boolean existsByFirstNameAndLastNameAndUserIdNot(String firstName,
                                                    String lastName,
                                                    Integer userId);


}
