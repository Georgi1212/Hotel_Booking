package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.RoomDto;
import com.app.hotelbooking.dto.RoomSizeTypeDto;
import com.app.hotelbooking.model.Room;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomMapper {

    private RoomSizeTypeMapper roomSizeTypeMapper;
    public RoomDto toDto(Room room) {
        return RoomDto.builder()
                .roomPrice(room.getRoomPrice())
                .description(room.getDescription())
                .numChildren(room.getNumChildren())
                .numAdults(room.getNumAdults())
                .roomSizeTypeDto(roomSizeTypeMapper.toDto(room.getRoomSizeType()))
                .build();
    }

    public Room toEntity(RoomDto roomDto){
        return Room.builder()
                .roomPrice(roomDto.getRoomPrice())
                .description(roomDto.getDescription())
                .numChildren(roomDto.getNumChildren())
                .numAdults(roomDto.getNumAdults())
                .roomSizeType(roomSizeTypeMapper.toEntity(roomDto.getRoomSizeTypeDto()))
                .build();
    }

    public List<RoomDto> toDtoCollection(List<Room> rooms){
        if(rooms == null){
            return Collections.emptyList();
        }

        return rooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
