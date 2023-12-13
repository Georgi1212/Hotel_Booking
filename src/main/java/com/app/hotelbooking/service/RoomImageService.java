package com.app.hotelbooking.service;

import com.app.hotelbooking.dto.RoomImageDto;
import com.app.hotelbooking.mapper.RoomImageMapper;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomImage;
import com.app.hotelbooking.repository.RoomImageRepository;
import com.app.hotelbooking.validation.ObjectFoundException;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
@AllArgsConstructor
public class RoomImageService {
    private final RoomImageRepository roomImageRepository;
    private final RoomImageMapper roomImageMapper;

    private final RoomService roomService;

    public byte[] getImageByRoomAndImageName(final Long hotelId, final Long roomId, final String imageName){
        final Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        final RoomImage roomImage = roomImageRepository.findFirstByImageNameAndRoom(imageName, room)
                .orElseThrow(() -> new ObjectFoundException("There is no such image"));

        return roomImage.getImageUrl();
    }
    public List<RoomImageDto> getAllImagesByHotelIdAndRoomId(final Long hotelId, final Long roomId){
        final Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        return roomImageMapper.toDtoCollection(roomImageRepository.findAllByRoom(room));
    }
    public void addImageToRoom(final Long hotelId, final Long roomId, final MultipartFile roomImage) throws IOException {
        final Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        final RoomImage image = new RoomImage();
        image.setImageUrl(roomImage.getBytes());
        image.setImageName(roomImage.getOriginalFilename());

        image.setRoom(room);
        room.getRoomImages().add(image);

        roomImageRepository.save(image);
    }

    public void deleteImageToRoom(final Long hotelId, final Long roomId, final String imageName){
        final Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        final RoomImage roomImageToDelete = roomImageRepository.findFirstByImageName(imageName)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such image"));

        boolean isImageFromRoom = roomImageToDelete.getRoom().equals(room);

        if(!isImageFromRoom){
            throw new ObjectNotFoundException("This image not belongs to that specific room");
        }

        room.getRoomImages().remove(roomImageToDelete);
        roomImageRepository.delete(roomImageToDelete);
    }
}
