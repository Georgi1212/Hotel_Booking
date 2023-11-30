package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.UserDto;
import com.app.hotelbooking.enums.UserType;
import com.app.hotelbooking.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getUserUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .userType(user.getUserType().toString())
                .build();
    }

    public User toEntity(UserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phoneNumber(userDto.getPhoneNumber())
                .dateOfBirth(userDto.getDateOfBirth())
                .address(userDto.getAddress())
                .userType(Enum.valueOf(UserType.class, userDto.getUserType()))
                .build();
    }
}
