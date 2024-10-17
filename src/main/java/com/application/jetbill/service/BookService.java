package com.application.jetbill.service;

import com.application.jetbill.dto.BookCreateUpdateDTO;
import com.application.jetbill.dto.BookDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookDetailsDTO> findAll();
    Page<BookDetailsDTO> paginate(Pageable pageable);
    BookDetailsDTO findById(Integer id);
    BookDetailsDTO create(BookCreateUpdateDTO bookCreateUpdateDTO);
    BookDetailsDTO update(Integer id, BookCreateUpdateDTO updateBookDTO);
    void delete(Integer id);
}
