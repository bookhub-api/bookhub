package com.application.jetbill.dto;


import com.application.jetbill.model.enums.ERole;
import lombok.Data;

@Data
public class UserProfileDto {

    private Integer id;
    private String email;
    private ERole role;

    private String firstName;
    private String lastName;

    private String shippingAddress;
    private String bio;
}
