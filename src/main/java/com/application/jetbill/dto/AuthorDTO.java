package com.application.jetbill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorDTO implements Serializable {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido debe tener 50 caracteres o menos")
    private String lastName;

    @NotBlank(message = "El biografia es obligatorio")
    @Size(max = 500, message = "La biografia debe tener 500 caracteres o menos")
    private String bio;
}
