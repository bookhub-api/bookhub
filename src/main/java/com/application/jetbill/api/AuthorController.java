package com.application.jetbill.api;


import com.application.jetbill.dto.AuthorDTO;
import com.application.jetbill.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll(){
        List<AuthorDTO> authorDTOS = authorService.getAll();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AuthorDTO>> paginateAuthor(@PageableDefault(size = 5, sort = "firstName")
                                                              Pageable pageable){
        Page<AuthorDTO> authorDTOPage = authorService.paginate(pageable);
        return new ResponseEntity<>(authorDTOPage, HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.create(authorDTO);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable Integer id) {
        AuthorDTO author = authorService.findById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Integer id,@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.update(id, authorDTO);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
