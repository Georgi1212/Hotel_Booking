package com.app.hotelbooking.service;

import com.app.hotelbooking.dto.RoomDto;
import com.app.hotelbooking.enums.RoomType;
import com.app.hotelbooking.mapper.HotelMapper;
import com.app.hotelbooking.mapper.RoomMapper;
import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomSizeType;
import com.app.hotelbooking.repository.HotelRepository;
import com.app.hotelbooking.repository.RoomImageRepository;
import com.app.hotelbooking.repository.RoomRepository;
import com.app.hotelbooking.repository.RoomSizeTypeRepository;
import com.app.hotelbooking.validation.ObjectFoundException;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    private final RoomImageRepository roomImageRepository;
    private final RoomSizeTypeRepository roomSizeTypeRepository;

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    private RoomSizeType findRoomSizeTypeByRoomTypeAndRoomCapacity(RoomType roomType, int roomCapacity){
        return roomSizeTypeRepository.findFirstByRoomTypeAndRoomCapacity(roomType, roomCapacity)
                .orElseThrow(() -> new ObjectFoundException("There is no such room type"));
    }
    public RoomDto toRoomDto(Room room){
        return roomMapper.toDto(room);
    }
    public Room getRoomByRoomId(final Long roomId){
        return roomRepository.findFirstById(roomId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such room"));
    }
    public Room getRoomByHotelAndRoomId(final Long hotelId, final Long roomId){
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        final Room room = roomRepository.findFirstById(roomId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such room"));

        if(!room.getHotel().getId().equals(hotel.getId())){
            throw new ObjectNotFoundException("There is no such room for this hotel");
        }

        return room;
    }

    public List<RoomDto> getAllRoomDtoByHotelId(final Long hotelId){
        Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        return roomMapper.toDtoCollection(roomRepository.findRoomsByHotel(hotel));
    }

    public List<Room> getAllRoomEntitiesByHotelId(final Long hotelId){
        Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        return roomRepository.findRoomsByHotel(hotel);
    }

    public void addRoom(final Long hotelId, final RoomDto roomDto){

        if(roomDto.getNumChildren() + roomDto.getNumAdults() != roomDto.getRoomSizeTypeDto().getRoomCapacity()){
            throw new IllegalArgumentException("The sum of number of children + number of adults cannot be less or greater than the room capacity");
        }

        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        //roomSizeTypeMapper.toEntity(roomDto.getRoomSizeTypeDto());
        final RoomSizeType roomSizeType = findRoomSizeTypeByRoomTypeAndRoomCapacity(Enum.valueOf(RoomType.class, roomDto.getRoomSizeTypeDto().getRoomType()),
                roomDto.getRoomSizeTypeDto().getRoomCapacity());

        final Room newRoom = roomMapper.toEntity(roomDto);

        newRoom.setHotel(hotel);
        newRoom.setRoomSizeType(roomSizeType);

        hotel.getRooms().add(newRoom);
        roomSizeType.getRooms().add(newRoom);

        roomRepository.save(newRoom);
    }

    public RoomDto updateRoom(final Long hotelId, final Long roomId, final RoomDto roomDto){

        if(roomDto.getNumChildren() + roomDto.getNumAdults() != roomDto.getRoomSizeTypeDto().getRoomCapacity()){
            throw new IllegalArgumentException("The sum of number of children + number of adults cannot be less or greater than the room capacity");
        }

        final RoomSizeType newRoomSizeType = findRoomSizeTypeByRoomTypeAndRoomCapacity(Enum.valueOf(RoomType.class, roomDto.getRoomSizeTypeDto().getRoomType()),
                roomDto.getRoomSizeTypeDto().getRoomCapacity());

        final Room room = getRoomByHotelAndRoomId(hotelId, roomId);

        final RoomSizeType oldRoomSizeType = room.getRoomSizeType();
        oldRoomSizeType.getRooms().remove(room);

        if(nonNull(roomDto.getRoomPrice())) room.setRoomPrice(roomDto.getRoomPrice());
        if(nonNull(roomDto.getDescription())) room.setDescription(roomDto.getDescription());

        room.setNumAdults(roomDto.getNumAdults());
        room.setNumChildren(roomDto.getNumChildren());

        room.setRoomSizeType(newRoomSizeType);
        newRoomSizeType.getRooms().add(room);

        roomRepository.save(room);

        return roomMapper.toDto(room);
    }

}
