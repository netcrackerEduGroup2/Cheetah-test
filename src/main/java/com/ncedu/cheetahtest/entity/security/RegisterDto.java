package com.ncedu.cheetahtest.entity.security;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {
    @NotNull
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, message = "Your password must contain 8 or more characters")
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String role;
}
