package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Max;
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
public class RoomDto {
    private BigDecimal roomPrice;

    @Size(max = 500)
    private String description;

    private int numChildren;

    private int numAdults;

    private RoomSizeTypeDto roomSizeTypeDto;
}
