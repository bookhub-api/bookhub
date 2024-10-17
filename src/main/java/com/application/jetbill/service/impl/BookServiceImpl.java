package com.application.jetbill.service.impl;


import com.application.jetbill.dto.BookCreateUpdateDTO;
import com.application.jetbill.dto.BookDetailsDTO;
import com.application.jetbill.exception.BadRequestException;
import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.mapper.BookMapper;
import com.application.jetbill.model.entity.Author;
import com.application.jetbill.model.entity.Book;
import com.application.jetbill.model.entity.Category;
import com.application.jetbill.repository.AuthorRepository;
import com.application.jetbill.repository.BookRepository;
import com.application.jetbill.repository.CategoryRepository;
import com.application.jetbill.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public List<BookDetailsDTO> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::toDetailsDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookDetailsDTO> paginate(Pageable pageable) {

        return bookRepository.findAll(pageable)
                .map(bookMapper::toDetailsDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDetailsDTO findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException("Ingrese un ID valido");
        }
        return bookRepository.findById(id)
                .map(bookMapper::toDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException("El Libro con ID: "+id+" No fue encontrado"));

    }

    @Transactional
    @Override
    public BookDetailsDTO create(BookCreateUpdateDTO bookCreateUpdateDTO) {
        // Asigna la categoría y el autor antes de guardar
        Category category = categoryRepository.findById(bookCreateUpdateDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + bookCreateUpdateDTO.getCategoryId()));

        Author author = authorRepository.findById(bookCreateUpdateDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + bookCreateUpdateDTO.getAuthorId()));

        // Convierte el DTO a entidad y asigna relaciones
        Book book = bookMapper.toEntity(bookCreateUpdateDTO);
        book.setCategory(category);
        book.setAuthor(author);
        book.setCreatedAt(LocalDateTime.now());

        try {
            // Guarda el libro y devuelve el DTO
            Book savedBook = bookRepository.save(book);
            return bookMapper.toDetailsDTO(savedBook);
        } catch (DataIntegrityViolationException e) {
            // Maneja violaciones de integridad, como restricciones de unicidad
            throw new BadRequestException("Ya existe un libro con el mismo título o slug.", e);
        }
    }


    @Transactional
    @Override
    public BookDetailsDTO update(Integer id, BookCreateUpdateDTO updatedBook) {
        // Obtiene el libro existente de la base de datos
        Book bookFromDb = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Asigna la categoría y el autor
        Category category = categoryRepository.findById(updatedBook.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + updatedBook.getCategoryId()));
        Author author = authorRepository.findById(updatedBook.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + updatedBook.getAuthorId()));

        // Actualiza los campos del libro existente
        bookFromDb.setTitle(updatedBook.getTitle());
        bookFromDb.setDescription(updatedBook.getDescription());
        bookFromDb.setPrice(updatedBook.getPrice());
        bookFromDb.setSlug(updatedBook.getSlug());
        bookFromDb.setCoverPath(updatedBook.getCoverPath());
        bookFromDb.setFilePath(updatedBook.getFilePath());
        bookFromDb.setCategory(category);
        bookFromDb.setAuthor(author);
        bookFromDb.setUpdatedAt(LocalDateTime.now());

        try {
            // Guarda el libro actualizado y devuelve el DTO
            Book savedBook = bookRepository.save(bookFromDb);
            return bookMapper.toDetailsDTO(savedBook);
        } catch (DataIntegrityViolationException e) {
            // Maneja violaciones de restricciones de unicidad
            throw new BadRequestException("Ya existe un libro con el mismo título o slug.", e);
        }
    }

    @Override
    public void delete(Integer id) {
        if(!bookRepository.existsById(id)){
            throw new ResourceNotFoundException("El Libro no fue encontrado");
        }
        bookRepository.deleteById(id);

    }
}
