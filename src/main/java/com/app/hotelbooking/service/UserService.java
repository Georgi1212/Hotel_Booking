package com.app.hotelbooking.service;

import com.app.hotelbooking.model.User;
import com.app.hotelbooking.repository.UserRepository;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserByUsername(final String username)
    {
        return userRepository.findFirstByUsername(username);
    }

    public String getUserTypeByEmail(final String email){
        User user = userRepository.findFirstByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Invalid user!"));

        return user.getUserType().toString();
    }

    public Optional<User> findUserByEmail(final String email){
        return userRepository.findFirstByEmail(email);
    }

    public User getUserByEmail(final String email){
        return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such user"));
    }

    public void addUser(final User user, final String verifyCode)
    {
        if(!user.getEmail().endsWith("@gmail.com")){
            throw new IllegalArgumentException("You can use only gmail accounts");
        }

        if(user == null)
            throw new ObjectNotFoundException("Invalid user!");

        User foundUser = userRepository.findFirstByEmail(user.getEmail()).orElse(null);
        if(foundUser != null) throw new ObjectNotFoundException("User already exists!");

        user.setEnabled(false);
        user.setVerifyCode(verifyCode);
        userRepository.save(user);
    }

    public void deleteUser(final User user)
    {
        if(user == null)
            throw new ObjectNotFoundException("Invalid user!");

        userRepository.findFirstByEmail(user.getEmail()).orElseThrow(() -> new ObjectNotFoundException("User does not exist!"));

        userRepository.delete(user);
    }

    public User updateUserByEmail(String username, String email, String first_name, String last_name, String phoneNumber, LocalDate dob, String address)
    {
        User user = userRepository.findFirstByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Invalid user!"));

        if(nonNull(username)) user.setUsername(username);
        if(nonNull(first_name)) user.setFirstName(first_name);
        if(nonNull(last_name)) user.setLastName(last_name);
        if(nonNull(phoneNumber)) user.setPhoneNumber(phoneNumber);
        if(nonNull(dob)) user.setDateOfBirth(dob);
        if(nonNull(address)) user.setAddress(address);

        userRepository.save(user);

        return user;

    }

    public boolean verifyEmail(final String verifyCode)
    {
        User user = userRepository.findFirstByVerifyCode(verifyCode).orElseThrow(() -> new ObjectNotFoundException("Invalid user!"));

        if(!user.isEnabled()){
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateUserPassword(String verifyCode, String password, String confirmPassword)
    {
        User user = userRepository.findFirstByVerifyCode(verifyCode).orElseThrow(() -> new ObjectNotFoundException("Invalid user!"));

        if(password.equals(confirmPassword)){
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        } else{
            return false;
        }
    }
}
