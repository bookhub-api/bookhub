package com.application.jetbill.service.impl;

import com.application.jetbill.dto.AuthorDTO;
import com.application.jetbill.exception.BadRequestException;
import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.mapper.AuthorMapper;
import com.application.jetbill.model.entity.Author;
import com.application.jetbill.repository.AuthorRepository;
import com.application.jetbill.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDTO> getAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorDTO> paginate(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(authorMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorDTO findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException("El ID proporcionado es nulo");
        }
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("El autor con ID "
                        + id + " no fue encontrado"));
    }

    @Transactional
    @Override
    public AuthorDTO create(AuthorDTO authorDTO) {
        if (authorRepository.existsByFirstNameAndLastName(
                authorDTO.getFirstName(), authorDTO.getLastName())) {
            throw new BadRequestException("El autor ya existe con el mismo nombre y apellido");
        }
        Author author = authorMapper.toEntity(authorDTO);
        author.setCreatedAt(LocalDateTime.now());
        Author authorSaved = authorRepository.save(author);
        return authorMapper.toDto(authorSaved);
    }

    @Override
    public AuthorDTO update(Integer id, AuthorDTO updateAuthorDTO) {
        Author authorFromDb = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El autor con ID " + id + " no fue encontrado"));
        try {
            if (authorRepository.existsByFirstNameAndLastNameAndIdNot(
                    updateAuthorDTO.getFirstName(), updateAuthorDTO.getLastName(), id)) {
                throw new BadRequestException("Ya existe un autor con el mismo nombre y apellido");
            }

        }catch (DataIntegrityViolationException e){
            throw new BadRequestException("Autor duplicado", e);
        }

        // Actualizar los campos necesarios
        authorFromDb.setFirstName(updateAuthorDTO.getFirstName());
        authorFromDb.setLastName(updateAuthorDTO.getLastName());
        authorFromDb.setBio(updateAuthorDTO.getBio());
        authorFromDb.setUpdatedAt(LocalDateTime.now());

        Author updatedAuthor = authorRepository.save(authorFromDb);
        return authorMapper.toDto(updatedAuthor);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("El autor con ID " + id + " no fue encontrado");
        }
        authorRepository.deleteById(id);
    }
}
