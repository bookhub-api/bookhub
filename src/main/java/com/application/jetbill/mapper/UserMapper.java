package com.application.jetbill.mapper;


import com.application.jetbill.dto.UserProfileDto;
import com.application.jetbill.dto.UserRegistrationDto;
import com.application.jetbill.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toEntity(UserRegistrationDto registrationDto){
        return modelMapper.map(registrationDto, User.class);

    }

    public UserProfileDto toDto(User user){
        UserProfileDto  userProfileDto =  modelMapper.map(user, UserProfileDto.class);
        if(user.getCustomer()!=null){
            userProfileDto.setFirstName(user.getCustomer().getFirstName());
            userProfileDto.setLastName(user.getCustomer().getLastName());
            userProfileDto.setShippingAddress(user.getCustomer().getShippingAddress());
            userProfileDto.setRole(user.getRole().getName());
        }
        if(user.getAuthor()!=null){
            userProfileDto.setFirstName(user.getAuthor().getFirstName());
            userProfileDto.setLastName(user.getAuthor().getLastName());
            userProfileDto.setBio(user.getAuthor().getBio());
            userProfileDto.setRole(user.getRole().getName());
        }
        return userProfileDto;
    }


}
