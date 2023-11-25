package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentialsDto {
    @Email()
    @NotBlank(message = "The email is required!")
    private String email;
    @NotBlank(message = "The password is required!")
    private String password;
}
