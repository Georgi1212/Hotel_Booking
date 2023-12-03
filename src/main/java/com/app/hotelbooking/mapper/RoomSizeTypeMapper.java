package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.RoomDto;
import com.app.hotelbooking.dto.RoomSizeTypeDto;
import com.app.hotelbooking.enums.RoomType;
import com.app.hotelbooking.enums.UserType;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomSizeType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomSizeTypeMapper {
    public RoomSizeTypeDto toDto(RoomSizeType roomSizeType) {
        return RoomSizeTypeDto.builder()
                .roomType(roomSizeType.getRoomType().toString())
                .roomCapacity(roomSizeType.getRoomCapacity())
                .build();
    }

    public RoomSizeType toEntity(RoomSizeTypeDto roomSizeTypeDto){
        return RoomSizeType.builder()
                .roomType(Enum.valueOf(RoomType.class, roomSizeTypeDto.getRoomType()))
                .roomCapacity(roomSizeTypeDto.getRoomCapacity())
                .build();
    }

    public List<RoomSizeTypeDto> toDtoCollection(List<RoomSizeType> roomSizeTypes){
        if(roomSizeTypes == null){
            return Collections.emptyList();
        }

        return roomSizeTypes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
