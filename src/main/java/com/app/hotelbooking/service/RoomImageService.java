package com.app.hotelbooking.service;

import com.app.hotelbooking.dto.RoomImageDto;
import com.app.hotelbooking.mapper.RoomImageMapper;
import com.app.hotelbooking.mapper.RoomMapper;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomImage;
import com.app.hotelbooking.repository.RoomImageRepository;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomImageService {
    private final RoomImageRepository roomImageRepository;
    private final RoomImageMapper roomImageMapper;

    private final RoomService roomService;

    public List<RoomImageDto> getAllImagesByHotelIdAndRoomId(final Long hotelId, final Long roomId){
        final Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        return roomImageMapper.toDtoCollection(roomImageRepository.findAllByRoom(room));
    }

    public void addImageToRoom(final Long roomId, final MultipartFile roomImage) throws IOException {
        final Room room = roomService.getRoomByRoomId(roomId);

        final RoomImage image = new RoomImage();
        image.setImageUrl(roomImage.getBytes());

        image.setRoom(room);
        room.getRoomImages().add(image);

        roomImageRepository.save(image);
    }

    //roomImageId?? is there any way to delete an image?
    public void deleteImageToRoom(final Long roomId, final Long roomImageId){
        final Room room = roomService.getRoomByRoomId(roomId);

        final RoomImage roomImageToDelete = roomImageRepository.findFirstById(roomImageId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such image"));

        boolean isImageFromRoom = roomImageToDelete.getRoom().equals(room);

        if(!isImageFromRoom){
            throw new ObjectNotFoundException("This image not belongs to that specific room");
        }

        room.getRoomImages().remove(roomImageToDelete);
        roomImageRepository.delete(roomImageToDelete);
    }
}
