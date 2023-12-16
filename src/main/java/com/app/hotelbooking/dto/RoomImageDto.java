package com.app.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomImageDto {
    private Long roomId;
    private String imageName;
    private byte[] imageUrl;
}
