package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.RoomImageDto;
import com.app.hotelbooking.model.RoomImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomImageMapper {
    public RoomImageDto toDto(RoomImage roomImage) {
        return RoomImageDto.builder()
                .imageName(roomImage.getImageName())
                .build();
    }

    public RoomImage toEntity(RoomImageDto roomImageDtoDto){
        return RoomImage.builder()
                .imageName(roomImageDtoDto.getImageName())
                .build();
    }

    public List<RoomImageDto> toDtoCollection(List<RoomImage> images){
        if(images == null){
            return Collections.emptyList();
        }

        return images.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
