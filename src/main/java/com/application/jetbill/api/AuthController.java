package com.application.jetbill.api;


import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.dto.UserRegistrationDto;
import com.application.jetbill.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    // Endpoint para registrar clientes
    @PostMapping("/register/customer")
    public ResponseEntity<UserProfileDto> registerCustomer(@Valid @RequestBody UserRegistrationDto registrationDto){
        UserProfileDto userProfile = userService.registerCustomer(registrationDto);
        return  new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }

    // Endpoint para registrar autores
    @PostMapping("/register/author")
    public ResponseEntity<UserProfileDto> registerAuthor(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        UserProfileDto userProfile = userService.registerAuthor(userRegistrationDto);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }


}
