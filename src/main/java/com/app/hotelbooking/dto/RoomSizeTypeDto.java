package com.app.hotelbooking.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomSizeTypeDto {

    @Pattern(regexp = "^(SINGLE|DOUBLE|TRIPLE|APARTMENT|PRESIDENTIAL)$", message = "Room types must be either 'SINGLE', 'DOUBLE', 'TRIPLE', 'APARTMENT' or 'PRESIDENTIAL'")
    private String roomType;

    @Min(value = 1, message = "Value must be at least 1")
    @Max(value = 4, message = "Value must be at most 4")
    private int roomCapacity;
}
