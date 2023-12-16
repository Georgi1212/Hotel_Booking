package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Past;
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
public class UpdateUserDto {

    @Size(min = 1, max = 32)
    private String username;
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
}
