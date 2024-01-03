package com.app.hotelbooking.controller;

import com.app.hotelbooking.dto.UpdateUserDto;
import com.app.hotelbooking.dto.UserDto;
import com.app.hotelbooking.mapper.UserMapper;
import com.app.hotelbooking.model.User;
import com.app.hotelbooking.service.EmailSenderService;
import com.app.hotelbooking.service.JwtService;
import com.app.hotelbooking.service.UserService;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;
    private final JwtService jwtService;


    @GetMapping("/userInfo/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        User user = userService.findUserByEmail(email).orElseThrow(() -> new ObjectNotFoundException("User does not exist!"));

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping("/userType")
    public ResponseEntity<String> getUserTypeByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.getUserTypeByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto){
        //check if userDto.getPassword() == userDto.getConfirmPassword()
        //check if password is valid based on regex
        //make endpoint http://localhost:8079/verifyEmail/{token}, in which the user will be added!!!
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int randomDigit = (int) (Math.random() * 10); // Generate a random digit (0-9)
            stringBuilder.append(randomDigit);
        }

        String verifyCode = stringBuilder.toString();

        User newUser = userMapper.toEntity(userDto);
        userService.addUser(newUser, verifyCode);

        String jwtToken = jwtService.generateToken(newUser);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added user. In order to use your account, please verify your email");
        response.put("token", jwtToken);

        String subject = "Email verification:";
        String body = "Click here, in order to verify your email: http://localhost:4200/users/verifyEmail/" + verifyCode;

        emailSenderService.sendEmail(newUser.getEmail(), subject, body);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/verifyEmail/{verifyCode}")
    public ResponseEntity<Object> verifyEmail(@PathVariable String verifyCode){
        boolean isVerified = userService.verifyEmail(verifyCode);
        Map<String, String> response = new HashMap<>();

        if(isVerified){
            response.put("message", "Successfully verified user");
        } else {
            response.put("message", "The user is already verified");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/updateUser/{email}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String email,
                                              @RequestBody UpdateUserDto toUpdateUserDto){

        User user = userService.updateUserByEmail(toUpdateUserDto.getUsername(), email,
                toUpdateUserDto.getFirstName(), toUpdateUserDto.getLastName(),
                toUpdateUserDto.getPhoneNumber(), toUpdateUserDto.getDateOfBirth(),
                toUpdateUserDto.getAddress());

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @DeleteMapping("/userInfo/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable String email){
        User user = userService.findUserByEmail(email).orElseThrow(() -> new ObjectNotFoundException("The is no such user"));
        userService.deleteUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully deleted user");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
