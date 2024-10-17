package com.application.jetbill.repository;

import com.application.jetbill.model.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public interface BookRepository  extends JpaRepository<Book, Integer> {


}
