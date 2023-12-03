package com.app.hotelbooking.service;

import com.app.hotelbooking.dto.RoomDto;
import com.app.hotelbooking.enums.RoomType;
import com.app.hotelbooking.enums.UserType;
import com.app.hotelbooking.mapper.HotelMapper;
import com.app.hotelbooking.mapper.RoomMapper;
import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomImage;
import com.app.hotelbooking.repository.HotelRepository;
import com.app.hotelbooking.repository.RoomImageRepository;
import com.app.hotelbooking.repository.RoomRepository;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    private final RoomImageRepository roomImageRepository;

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public Room getRoomByRoomId(final Long roomId){
        return roomRepository.findFirstById(roomId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such room"));
    }
    public RoomDto getRoomByHotelAndRoomId(final Long hotelId, final Long roomId){
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        final Room room = roomRepository.findFirstById(roomId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such room"));

        if(!room.getHotel().getId().equals(hotel.getId())){
            throw new ObjectNotFoundException("There is no such room for this hotel");
        }

        return roomMapper.toDto(room);
    }

    public List<RoomImage> getAllImagesByRoomId(final Long roomId){
        final Room room = getRoomByRoomId(roomId);

        return roomImageRepository.findRoomImagesByRoom(room);
    }

    public void addImageToRoom(final Long roomId, final MultipartFile roomImage) throws IOException {
        final Room room = getRoomByRoomId(roomId);

        final RoomImage image = new RoomImage();
        image.setImageUrl(roomImage.getBytes());

        image.setRoom(room);
        room.getRoomImages().add(image);

        roomImageRepository.save(image);
    }

    public void deleteImageToRoom(final Long roomId, final Long roomImageId){
        final Room room = getRoomByRoomId(roomId);

        final RoomImage roomImageToDelete = roomImageRepository.findFirstById(roomImageId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such image"));

        boolean isImageFromRoom = roomImageToDelete.getRoom().equals(room);

        if(!isImageFromRoom){
            throw new ObjectNotFoundException("This image not belongs to that specific room");
        }

        room.getRoomImages().remove(roomImageToDelete);
        roomImageRepository.delete(roomImageToDelete);
    }

    public RoomDto updateRoom(final Long roomId, final RoomDto roomDto){

        if(roomDto.getNumChildren() + roomDto.getNumAdults() != roomDto.getRoomSizeTypeDto().getRoomCapacity()){
            throw new IllegalArgumentException("The sum of number of children + number of adults cannot be less or greater than the room capacity");
        }

        final Room room = getRoomByRoomId(roomId);

        if(nonNull(roomDto.getRoomPrice())) room.setRoomPrice(roomDto.getRoomPrice());
        if(nonNull(roomDto.getDescription())) room.setDescription(room.getDescription());

        room.setNumAdults(roomDto.getNumAdults());
        room.setNumChildren(roomDto.getNumChildren());

        if(nonNull(roomDto.getRoomSizeTypeDto().getRoomType()))
            room.getRoomSizeType().setRoomType(Enum.valueOf(RoomType.class, roomDto.getRoomSizeTypeDto().getRoomType()));

        room.getRoomSizeType().setRoomCapacity(roomDto.getRoomSizeTypeDto().getRoomCapacity());

        return roomMapper.toDto(room);
    }



}
