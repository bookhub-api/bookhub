package com.application.jetbill.mapper;

import com.application.jetbill.dto.BookCreateUpdateDTO;
import com.application.jetbill.dto.BookDetailsDTO;
import com.application.jetbill.model.entity.Book;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


@Component
public class BookMapper {
    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        //Configurar ModelMapper para usar estrategia de coincidencia estricta
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public BookDetailsDTO toDetailsDTO(Book book) {
        BookDetailsDTO bookDetailsDTO =  modelMapper.map(book, BookDetailsDTO.class);
        bookDetailsDTO.setAuthorName(book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName());
        bookDetailsDTO.setCategoryName(book.getCategory().getName());
        return bookDetailsDTO;
    }

    public Book toEntity(BookCreateUpdateDTO bookCreateUpdateDTO) {
        return modelMapper.map(bookCreateUpdateDTO, Book.class);
    }

    public BookCreateUpdateDTO toCreateUpdateDTO(Book book) {
        return modelMapper.map(book, BookCreateUpdateDTO.class);
    }

}
