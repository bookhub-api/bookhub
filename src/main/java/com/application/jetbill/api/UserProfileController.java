package com.application.jetbill.api;

import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;


    // Obtener el perfil de un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable Integer id) {
        UserProfileDto userProfile = userService.getUserProfileById(id);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    // Actualizar el perfil del usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateProfile(@PathVariable Integer id, @RequestBody @Valid UserProfileDto userProfileDTO) {
        UserProfileDto updatedProfile = userService.updateUserProfile(id, userProfileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }


}
