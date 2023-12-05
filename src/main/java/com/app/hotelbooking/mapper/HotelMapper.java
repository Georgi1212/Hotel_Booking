package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.HotelDto;
import com.app.hotelbooking.model.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HotelMapper {

    public HotelDto toDto(Hotel hotel) {
        return HotelDto.builder()
                .hotelName(hotel.getHotelName())
                .street(hotel.getStreet())
                .city(hotel.getCity())
                .country(hotel.getCountry())
                .hotelDescription(hotel.getDescription())
                .hotelImageUrl(hotel.getHotelImageUrl())
                .rate(hotel.getRate().toString())
                .build();
    }

    public Hotel toEntity(HotelDto hotelDto){
        return Hotel.builder()
                .hotelName(hotelDto.getHotelName())
                .street(hotelDto.getStreet())
                .city(hotelDto.getCity())
                .country(hotelDto.getCountry())
                .description(hotelDto.getHotelDescription())
                .hotelImageUrl(hotelDto.getHotelImageUrl())
                .rate(new BigDecimal(hotelDto.getRate()))
                .build();
    }

    public List<HotelDto> toDtoCollection(List<Hotel> hotels){
        if(hotels == null){
            return Collections.emptyList();
        }

        return hotels.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
