package com.application.jetbill.service;

import com.application.jetbill.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAll();
    Page<AuthorDTO> paginate(Pageable pageable);
    AuthorDTO findById(Integer id);
    AuthorDTO create(AuthorDTO AuthorDTO);
    AuthorDTO update(Integer id, AuthorDTO updateAuthorDTO);
    void delete(Integer id);
}
