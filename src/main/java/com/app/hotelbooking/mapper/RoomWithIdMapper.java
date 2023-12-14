package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.RoomDtoWithId;
import com.app.hotelbooking.model.Room;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomWithIdMapper {

    private RoomSizeTypeMapper roomSizeTypeMapper;
    public RoomDtoWithId toDto(Room room) {
        return RoomDtoWithId.builder()
                .roomId(room.getId())
                .roomPrice(room.getRoomPrice())
                .description(room.getDescription())
                .numChildren(room.getNumChildren())
                .numAdults(room.getNumAdults())
                .roomSizeTypeDto(roomSizeTypeMapper.toDto(room.getRoomSizeType()))
                .build();
    }

    public List<RoomDtoWithId> toDtoCollection(List<Room> rooms){
        if(rooms == null){
            return Collections.emptyList();
        }

        return rooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
