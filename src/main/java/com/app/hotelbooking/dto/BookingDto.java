package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long bookingId;
    @Email()
    @Size(max = 64)
    private String userEmail;
    @Size(max = 32)
    private String userFirstName;
    @Size(max = 32)
    private String userLastName;
    @Size(max = 15)
    private String userPhoneNumber;
    @Size(max = 64)
    private String userAddress;
    @Size(max = 255)

    @Email()
    @Size(max = 64)
    private String hostEmail;
    @Size(max = 32)
    private String hostFirstName;
    @Size(max = 32)
    private String hostLastName;
    @Size(max = 15)
    private String hostPhoneNumber;
    @Size(max = 64)
    private String hostAddress;
    @Size(max = 255)

    private Long hotelId;
    private String hotelName;
    @Size(max = 64)
    private String hotelStreet;
    @Size(max = 64)
    private String hotelCity;
    @Size(max = 100)
    private String hotelCountry;

    private Long roomId;
    private BigDecimal roomPrice;
    private String roomType;
    private int roomCapacity;

    private LocalDate check_in;
    private LocalDate check_out;

    private BigDecimal sumPrice;

    private Timestamp createdAt;




}
