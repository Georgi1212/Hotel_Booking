package com.app.hotelbooking.service;


import com.app.hotelbooking.dto.HotelDto;
import com.app.hotelbooking.imageUtils.ImageUtils;
import com.app.hotelbooking.mapper.HotelMapper;
import com.app.hotelbooking.mapper.RoomMapper;
import com.app.hotelbooking.model.*;
import com.app.hotelbooking.repository.HotelRepository;
import com.app.hotelbooking.repository.RoomRepository;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.DataFormatException;

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

    public HotelDto toHotelDto(Hotel hotel){
        return hotelMapper.toDto(hotel);
    }

    public HotelDto getHotelById(final Long hotelId){

        return hotelMapper.toDto(
                hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"))
        );
    }

    public List<HotelDto> getAllHotelsByEmail(final String email){
        final User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User does not exist"));

        if(!user.getUserType().toString().equals("ADMIN")){
            throw new IllegalArgumentException("Only admins could be hotel hosts");
        }

        return hotelMapper.toDtoCollection(hotelRepository.findHotelsByHost(user));
    }

    public List<HotelDto> getHotelsByCountryAndCity(final String country, final String city){
        return hotelMapper.toDtoCollection(hotelRepository.findHotelsByCountryAndCity(country, city));
    }

    public List<HotelDto> getHotelsByCountry(final String country){
        return hotelMapper.toDtoCollection(hotelRepository.findHotelsByCountry(country));
    }

    public byte[] getHotelImage(final Long hotelId){
        Hotel hotel = hotelMapper.toEntity(getHotelById(hotelId));

        byte[] hotelImage = hotel.getHotelImageUrl();

        try {
            return ImageUtils.decompressImage(hotelImage);
        } catch (DataFormatException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
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
            throw new IllegalArgumentException("The user type of the host must be ADMIN. Only admins can add hotels.");
        }

        newHotel.setHost(userHost);

        userHost.getHotels().add(newHotel);

        hotelRepository.save(newHotel);

    }
    public void addImageToHotel(final Long hotelId, final MultipartFile hotelImage) throws IOException {
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        hotel.setHotelImageUrl(ImageUtils.compressImage(hotelImage.getBytes()));

        hotelRepository.save(hotel);
    }

    public HotelDto updateHotel(final Long hotelId, final String hotelName, final String hotelStreet,
                                    final String hotelCity, final String hotelCountry,
                                    final String hotelDescription, final BigDecimal hotelRate){

        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        if (nonNull(hotelName)) hotel.setHotelName(hotelName);
        if (nonNull(hotelStreet)) hotel.setStreet(hotelStreet);
        if (nonNull(hotelCity)) hotel.setCity(hotelCity);
        if (nonNull(hotelCountry)) hotel.setCountry(hotelCountry);
        if (nonNull(hotelDescription)) hotel.setDescription(hotelDescription);
        if (nonNull(hotelRate)) hotel.setRate(hotelRate);

        hotelRepository.save(hotel);

        return hotelMapper.toDto(hotel);
    }

    public byte[] updateHotelImage(final Long hotelId, final MultipartFile newImage) throws IOException, DataFormatException {
        final Hotel hotel = hotelRepository.findFirstById(hotelId)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such hotel"));

        hotel.setHotelImageUrl(ImageUtils.compressImage(newImage.getBytes()));
        hotelRepository.save(hotel);

        return ImageUtils.decompressImage(hotel.getHotelImageUrl());
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
        final Room roomToDelete = roomService.getRoomByHotelAndRoomId(hotelId, roomId);
        final Hotel hotelOfTheRoom = roomToDelete.getHotel();

        hotelOfTheRoom.getRooms().remove(roomToDelete);
        roomRepository.delete(roomToDelete);
    }

}
