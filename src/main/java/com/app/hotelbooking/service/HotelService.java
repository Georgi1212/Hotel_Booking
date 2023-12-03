package com.app.hotelbooking.service;


import com.app.hotelbooking.dto.HotelDto;
import com.app.hotelbooking.dto.RoomDto;
import com.app.hotelbooking.enums.UserType;
import com.app.hotelbooking.mapper.HotelMapper;
import com.app.hotelbooking.mapper.RoomMapper;
import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomImage;
import com.app.hotelbooking.model.User;
import com.app.hotelbooking.repository.HotelRepository;
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
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    private final UserService userService;

    public HotelDto getHotelById(final Long hotelId){
        return hotelMapper.toDto(
                hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"))
        );
    }

    public List<HotelDto> getAllHotelsByEmail(final String email){
        final User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User does not exist"));

        return hotelMapper.toDtoCollection(hotelRepository.findHotelsByHost(user));
    }

    public List<HotelDto> getHotelsByCountryAndCity(final String country, final String city){
        return hotelMapper.toDtoCollection(hotelRepository.findHotelsByCountryAndCity(country, city));
    }

    public List<HotelDto> getHotelsByCountry(final String country){
        return hotelMapper.toDtoCollection(hotelRepository.findHotelsByCountry(country));
    }

    public void addHotel(final HotelDto hotelDto, final String email){
        final Hotel newHotel = hotelMapper.toEntity(hotelDto);

        if(newHotel == null){
            throw new IllegalArgumentException("Incorrect data");
        }

        final User userHost = userService.findUserByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User does not exist"));

        final String userType = userHost.getUserType().toString();

        if(!userType.equals("ADMIN")){
            throw new IllegalArgumentException("The user type of the host must be ADMIN");
        }

        newHotel.setHost(userHost);

        userHost.getHotels().add(newHotel);

        hotelRepository.save(newHotel);

    }

    public void addRoom(final Long hotelId, final RoomDto roomDto){

        if(roomDto.getNumChildren() + roomDto.getNumAdults() != roomDto.getRoomSizeTypeDto().getRoomCapacity()){
            throw new IllegalArgumentException("The sum of number of children + number of adults cannot be less or greater than the room capacity");
        }

        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        final Room newRoom = roomMapper.toEntity(roomDto);

        newRoom.setHotel(hotel);
        hotel.getRooms().add(newRoom);

        roomRepository.save(newRoom);
    }

    public void addImageToHotel(final Long hotelId, final MultipartFile hotelImage) throws IOException {
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        final RoomImage image = new RoomImage();
        hotel.setHotelImageUrl(hotelImage.getBytes());

        hotelRepository.save(hotel);
    }

    public HotelDto updateHotel(final Long hotelId, final String hotelName, final String hotelStreet,
                                    final String hotelCity, final String hotelCountry,
                                    final String hotelDescription, final BigDecimal hotelRate,
                                    final boolean isPetAvailable){

        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        if (nonNull(hotelName)) hotel.setHotelName(hotelName);
        if (nonNull(hotelStreet)) hotel.setStreet(hotelStreet);
        if (nonNull(hotelCity)) hotel.setCity(hotelCity);
        if (nonNull(hotelCountry)) hotel.setCountry(hotelCountry);
        if (nonNull(hotelDescription)) hotel.setDescription(hotelDescription);
        if (nonNull(hotelRate)) hotel.setRate(hotelRate);

        hotel.setPetAvailable(isPetAvailable);

        hotelRepository.save(hotel);

        return hotelMapper.toDto(hotel);
    }

    public void updateHotelImage(final Long hotelId, final MultipartFile newImage) throws IOException {
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        hotel.setHotelImageUrl(newImage.getBytes());
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long hotelId){
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        try {
            hotelRepository.delete(hotel);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteRoomFromHotel(Long hotelId, Long roomId){
        final Room roomToDelete = roomMapper.toEntity(roomService.getRoomByHotelAndRoomId(hotelId, roomId));
        final Hotel hotelOfTheRoom = roomToDelete.getHotel();

        hotelOfTheRoom.getRooms().remove(roomToDelete);
        roomRepository.delete(roomToDelete);
    }

}
