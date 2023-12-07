package com.app.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToCreateBookingDto {
    private Long hotelId;
    private Long roomId;
    private LocalDate check_in;
    private LocalDate check_out;
}
