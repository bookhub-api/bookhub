package com.application.jetbill.service;

import com.application.jetbill.dto.AuthResponseDTO;
import com.application.jetbill.dto.LoginDTO;
import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.dto.UserRegistrationDto;


public interface UserService {
    //Register a customer
    UserProfileDto registerCustomer(UserRegistrationDto registrationDto);
    //Register an actor
    UserProfileDto registerAuthor(UserRegistrationDto registrationDto);
    //update user profile
    UserProfileDto updateUserProfile(Integer id, UserProfileDto userProfileDto);
    // get user profile by id
    UserProfileDto getUserProfileById(Integer id);
    AuthResponseDTO login(LoginDTO loginDTO);
}
