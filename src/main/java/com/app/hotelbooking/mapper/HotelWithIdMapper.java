package com.app.hotelbooking.mapper;

import com.app.hotelbooking.dto.HotelDto;
import com.app.hotelbooking.dto.HotelDtoWithId;
import com.app.hotelbooking.model.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HotelWithIdMapper {
    public HotelDtoWithId toDto(Hotel hotel) {
        return HotelDtoWithId.builder()
                .hotelId(hotel.getId())
                .hotelName(hotel.getHotelName())
                .street(hotel.getStreet())
                .city(hotel.getCity())
                .country(hotel.getCountry())
                .hotelDescription(hotel.getDescription())
                .hotelImageUrl(hotel.getHotelImageUrl())
                .rate(hotel.getRate().toString())
                .build();
    }

    public List<HotelDtoWithId> toDtoCollection(List<Hotel> hotels){
        if(hotels == null){
            return Collections.emptyList();
        }

        return hotels.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
