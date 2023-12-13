package com.app.hotelbooking.model;


import com.app.hotelbooking.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
//added private field called "enabled" of type boolean, in order to see if the user's account is enabled

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    @Size(min = 1, max = 32)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Email()
    @Size(max = 64)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    @Size(max = 32)
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 32)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    @Size(max = 15)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    @Past
    private LocalDate dateOfBirth;

    @Column(name = "address")
    @Size(max = 64)
    private String address;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "last_modified_date", nullable = false)
    @UpdateTimestamp
    private Timestamp last_modified_date;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "verify_code", nullable = false)
    @Size(max = 16)
    private String verifyCode;

    @OneToMany(mappedBy = "host", cascade = CascadeType.REMOVE)
    private Set<Hotel> hotels;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Booking> bookings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userType.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
        //at first, it might be false, because through the email message, the user will verify their email and the account will be enabled and this field will become true
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getUserUsername(){
        return username;
    }
}
