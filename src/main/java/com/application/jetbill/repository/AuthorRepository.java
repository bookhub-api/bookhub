package com.application.jetbill.repository;

import com.application.jetbill.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, Integer id);

}
