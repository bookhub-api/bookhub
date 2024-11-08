package com.application.jetbill.service.impl;


import com.application.jetbill.dto.AuthResponseDTO;
import com.application.jetbill.dto.LoginDTO;
import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.dto.UserRegistrationDto;
import com.application.jetbill.exception.BadRequestException;
import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.mapper.UserMapper;
import com.application.jetbill.model.entity.Author;
import com.application.jetbill.model.entity.Customer;
import com.application.jetbill.model.entity.Role;
import com.application.jetbill.model.entity.User;
import com.application.jetbill.model.enums.ERole;
import com.application.jetbill.repository.RoleRepository;
import com.application.jetbill.repository.UserRepository;
import com.application.jetbill.security.UserPrincipal;
import com.application.jetbill.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;



    @Override
    @Transactional
    public UserProfileDto registerCustomer(UserRegistrationDto registrationDto) {
        return registerUserWithRole(registrationDto, ERole.CUSTOMER);
    }

    @Override
    @Transactional
    public UserProfileDto registerAuthor(UserRegistrationDto registrationDto) {
        return registerUserWithRole(registrationDto, ERole.AUTHOR);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(Integer id, UserProfileDto userProfileDto) {
        // Buscar el usuario por su ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Actualizar los campos específicos del perfil
        if (user.getCustomer() != null) {
            user.getCustomer().setFirstName(userProfileDto.getFirstName());
            user.getCustomer().setLastName(userProfileDto.getLastName());
            user.getCustomer().setShippingAddress(userProfileDto.getShippingAddress());
        }

        if (user.getAuthor() != null) {
            user.getAuthor().setFirstName(userProfileDto.getFirstName());
            user.getAuthor().setLastName(userProfileDto.getLastName());
            user.getAuthor().setBio(userProfileDto.getBio());
        }

        // Guardar los cambios en la base de datos
        User updatedUser = userRepository.save(user);

        // Convertir el usuario actualizado a UserProfileDTO para la respuesta
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfileById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        // Convertir a UserProfileDTO para la respuesta
        return userMapper.toDto(user);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        //autenticar al usuario utilizando  AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        // Una vez autenticado, el objeto authentication obtiene la información del usuario autenticado
        UserPrincipal userPrincipal = (UserPrincipal)  authentication.getPrincipal();
        User user = userPrincipal.getUser();
        return userMapper.toAuthResponseDTO(user, "uhhfdfoh");
    }

    // Método genérico para registrar un usuario con un rol específico
    private UserProfileDto registerUserWithRole(UserRegistrationDto registrationDto, ERole roleEnum) {
        // Verificar si el email ya está registrado
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        // Asignar el rol del usuario
        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        // Cifrar la contraseña
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // Convertir el DTO a una entidad User
        User user = userMapper.toEntity(registrationDto);
        user.setRole(role); // Asignar el rol al usuario

        // Asignar la entidad específica basada en el rol
        if (roleEnum == ERole.CUSTOMER) {
            Customer customer = new Customer();
            customer.setFirstName(registrationDto.getFirstName());
            customer.setLastName(registrationDto.getLastName());
            customer.setShippingAddress(registrationDto.getShippingAddress());
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUser(user);
            user.setCustomer(customer);
        } else if (roleEnum == ERole.AUTHOR) {
            Author author = new Author();
            author.setFirstName(registrationDto.getFirstName());
            author.setLastName(registrationDto.getLastName());
            author.setBio(registrationDto.getBio());
            author.setCreatedAt(LocalDateTime.now());
            author.setUser(user);
            user.setAuthor(author);
        }

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Convertir el usuario registrado a UserProfileDTO para la respuesta
        return userMapper.toDto(savedUser);


    }
}
