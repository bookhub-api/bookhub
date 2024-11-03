package com.application.jetbill.service;

import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.dto.UserRegistrationDto;


public interface UserService {
    UserProfileDto registerCustomer(UserRegistrationDto registrationDto);
    UserProfileDto registerAuthor(UserRegistrationDto registrationDto);
    UserProfileDto updateUserProfile(Integer id, UserProfileDto userProfileDto);
    UserProfileDto getUserProfileById(Integer id);
}
