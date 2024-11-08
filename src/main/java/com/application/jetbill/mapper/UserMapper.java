package com.application.jetbill.mapper;


import com.application.jetbill.dto.AuthResponseDTO;
import com.application.jetbill.dto.LoginDTO;
import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.dto.UserRegistrationDto;
import com.application.jetbill.model.entity.Author;
import com.application.jetbill.model.entity.Customer;
import com.application.jetbill.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toEntity(UserRegistrationDto registrationDto) {
        return modelMapper.map(registrationDto, User.class);

    }

    // Convertir de UserRegistrationDTO a User (solo mapeo directo)
    public User toUserEntity(UserRegistrationDto registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    // Convertir de User a UserProfileDTO para la respuesta
    public UserProfileDto toDto(User user) {
        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);
        // Si es cliente, asignar los datos de cliente
        if (user.getCustomer() != null) {
            userProfileDto.setFirstName(user.getCustomer().getFirstName());
            userProfileDto.setLastName(user.getCustomer().getLastName());
            userProfileDto.setShippingAddress(user.getCustomer().getShippingAddress());
            userProfileDto.setRole(user.getRole().getName());
        }
        // Si es autor, asignar los datos de autor
        if (user.getAuthor() != null) {
            userProfileDto.setFirstName(user.getAuthor().getFirstName());
            userProfileDto.setLastName(user.getAuthor().getLastName());
            userProfileDto.setBio(user.getAuthor().getBio());
            userProfileDto.setRole(user.getRole().getName());
        }
        return userProfileDto;
    }

    // Convertir de User a AuthResponseDTO para la respuesta de autenticación
    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token); // Asignar el token
        //obtener nombre y apellido
        String firstName = getFirstName(user);
        String lastName = getLastName(user);

        /*String firstName = (user.getCustomer() != null) ? user.getCustomer().getFirstName()
                : (user.getAuthor() != null) ? user.getAuthor().getFirstName() : "Admin";

        String lastName = (user.getCustomer() != null) ? user.getCustomer().getLastName()
                : (user.getAuthor() != null) ? user.getAuthor().getLastName() : "User";*/

        // Si es cliente, asignar los datos de cliente

        authResponseDTO.setFirstName(firstName);
        authResponseDTO.setLastName(lastName);
        authResponseDTO.setRole(user.getRole().getName().name());


        return authResponseDTO;
    }

    private String getFirstName(User user) {
        return Optional.ofNullable(user.getCustomer())
                .map(Customer::getFirstName)
                .orElse(Optional.ofNullable(user.getAuthor())
                        .map(Author::getFirstName)
                        .orElse("Admin"));

    }

    private String getLastName(User user) {
        return Optional.ofNullable(user.getCustomer())
                .map(Customer::getLastName)
                .orElse(Optional.ofNullable(user.getAuthor())
                        .map(Author::getLastName)
                        .orElse("User"));
 }

}