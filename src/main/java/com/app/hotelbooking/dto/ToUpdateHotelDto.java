package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToUpdateHotelDto {

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

    private String rate;
}
