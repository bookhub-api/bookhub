package com.application.jetbill.repository;

import com.application.jetbill.model.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Integer>{
    List<Collection> findByCustomerId(Integer customerId);
}
