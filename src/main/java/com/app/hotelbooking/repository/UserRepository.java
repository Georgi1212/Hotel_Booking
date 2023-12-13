package com.app.hotelbooking.repository;

import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsername(final String username);
    Optional<User> findFirstByEmail(final String email);
    Optional<User> findByEmail(final String email);

    Optional<User> findFirstByVerifyCode(@Size(max = 16) String verifyCode);
}
