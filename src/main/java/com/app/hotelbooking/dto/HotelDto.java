package com.app.hotelbooking.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

    @Size(max = 255)
    private String hotelName;

    @Size(max = 64)
    private String street;

    @Size(max = 64)
    private String city;

    @Size(max = 100)
    private String country;

    @Size(max = 2000)
    private String hotelDescription;

    //@Lob
    private byte[] hotelImageUrl;

    @Min(value = 1)
    @Max(value = 10)
    private BigDecimal rate;

    private boolean isPetAvailable;
}
