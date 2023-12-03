package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Size(min = 1, max = 32)
    private String username;
    @Email()
    @Size(max = 64)
    private String email;
    @Size(min = 6, max = 10)
    private String password;
    @Size(min = 6, max = 10)
    private String confirmPassword;
    @Size(max = 32)
    private String firstName;
    @Size(max = 32)
    private String lastName;
    @Size(max = 15)
    private String phoneNumber;
    @Past
    private LocalDate dateOfBirth;
    @Size(max = 64)
    private String address;
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either 'USER' or 'ADMIN'")
    private String userType; // USER or ADMIN
}
