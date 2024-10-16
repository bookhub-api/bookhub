package com.application.jetbill.mapper;


import com.application.jetbill.dto.AuthorDTO;
import com.application.jetbill.model.entity.Author;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorMapper {
    private final ModelMapper modelMapper;

    public AuthorDTO toDto(Author author){
        return modelMapper.map(author, AuthorDTO.class);
    }

    public Author toEntity(AuthorDTO authorDTO){
        return modelMapper.map(authorDTO, Author.class);
    }
}
