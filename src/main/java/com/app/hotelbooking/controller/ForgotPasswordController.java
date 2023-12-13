package com.app.hotelbooking.controller;

import com.app.hotelbooking.dto.EmailDto;
import com.app.hotelbooking.dto.PasswordDto;
import com.app.hotelbooking.model.User;
import com.app.hotelbooking.service.EmailSenderService;
import com.app.hotelbooking.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ForgotPasswordController {

    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @PostMapping("/sendPasswordResetEmail")
    public ResponseEntity<Object> sendPasswordResetEmail(@Valid @RequestBody EmailDto emailDto){

        Map<String, String> response = new HashMap<>();

        User user = userService.getUserByEmail(emailDto.getEmail());
        String verifyCode = user.getVerifyCode();

        if(isNull(user)){
            response.put("message", "User with this provided email is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        String subject = "Password reset:";
        String body = "Click here, in order to reset your password: http://localhost:4200/resetPassword/" + verifyCode;

        emailSenderService.sendEmail(emailDto.getEmail(), subject, body);

        response.put("message", "Email for resetting your password is sent. Please, check your email");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/resetPassword/{verifyCode}")
    public ResponseEntity<Object> resetPassword(@PathVariable String verifyCode, @Valid @RequestBody PasswordDto passwordDto) {

        boolean isPasswordReset = userService.updateUserPassword(verifyCode, passwordDto.getPassword(), passwordDto.getConfirm_password());
        Map<String, String> response = new HashMap<>();

        if(!isPasswordReset){
            response.put("message", "The password field and confirm password field must be equal");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else{
            response.put("message", "Password updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
