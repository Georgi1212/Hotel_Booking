package com.app.hotelbooking.controller;

import com.app.hotelbooking.dto.*;
import com.app.hotelbooking.model.Booking;
import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomImage;
import com.app.hotelbooking.service.BookingService;
import com.app.hotelbooking.service.HotelService;
import com.app.hotelbooking.service.RoomImageService;
import com.app.hotelbooking.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hotels")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class HotelBookingController {
    private final HotelService hotelService;
    private final RoomService roomService;
    private final RoomImageService roomImageService;
    private final BookingService bookingService;

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId) {

        return new ResponseEntity<>(hotelService.getHotelById(hotelId), HttpStatus.OK);
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<HotelDto>> getHotelsByUserId(@PathVariable String userEmail) {

        return new ResponseEntity<>(hotelService.getAllHotelsByEmail(userEmail), HttpStatus.OK);
    }

    @GetMapping("/{country}/{city}")
    public ResponseEntity<List<HotelDto>> getHotelsByCountryAndCity(@PathVariable String country,
                                                                    @PathVariable String city) {

        return new ResponseEntity<>(hotelService.getHotelsByCountryAndCity(country, city), HttpStatus.OK);
    }

    @GetMapping("/{country}")
    public ResponseEntity<List<HotelDto>> getHotelsByCountryAndCity(@PathVariable String country) {

        return new ResponseEntity<>(hotelService.getHotelsByCountry(country), HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<RoomDto> getRoomByHotelIdAndRoomId(@PathVariable Long hotelId,
                                                           @PathVariable Long roomId) {
        Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);
        return new ResponseEntity<>(roomService.toRoomDto(room), HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<RoomDto>> getRoomsByHotelId(@PathVariable Long hotelId) {
        return new ResponseEntity<>(roomService.getAllRoomDtoByHotelId(hotelId), HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/rooms/{roomId}/images")
    public ResponseEntity<List<RoomImageDto>> getAllImagesByHotelIdAndRoomId(@PathVariable Long hotelId,
                                                                             @PathVariable Long roomId) {
        return new ResponseEntity<>(roomImageService.getAllImagesByHotelIdAndRoomId(hotelId, roomId), HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/rooms/{roomId}/bookings")
    public ResponseEntity<List<BookingDto>> getBookingsByHotelIdAndRoomId(@PathVariable Long hotelId,
                                                                          @PathVariable Long roomId){
        return new ResponseEntity<>(bookingService.getBookingsByHotelIdAndRoomId(hotelId, roomId), HttpStatus.OK);
    }

    @GetMapping("{email}/bookings")
    public ResponseEntity<List<BookingDto>> getBookingsByUserEmail(@PathVariable String email){
        return new ResponseEntity<>(bookingService.getBookingsByUserEmail(email), HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/bookings")
    public ResponseEntity<List<BookingDto>> getBookingsByHotelId(@PathVariable Long hotelId){
        return new ResponseEntity<>(bookingService.getBookingsByHotelId(hotelId), HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/rooms/{roomId}/bookings/timePeriod")
    public ResponseEntity<List<BookingDto>> getBookingsByHotelIdAndRoomIdForTimePeriod(@PathVariable Long hotelId,
                                                                                       @PathVariable Long roomId,
                                                                                       @RequestParam LocalDate startDate,
                                                                                       @RequestParam LocalDate endDate){
        return new ResponseEntity<>(bookingService.
                getBookingsByHotelIdAndRoomIdForTimePeriod(hotelId, roomId, startDate, endDate),
                HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/bookings/timePeriod")
    public ResponseEntity<List<BookingDto>> getBookingsByHotelIdForTimePeriod(@PathVariable Long hotelId,
                                                                              @RequestParam LocalDate startDate,
                                                                              @RequestParam LocalDate endDate){
        return new ResponseEntity<>(bookingService.
                getBookingsByHotelIdForTimePeriod(hotelId, startDate, endDate),
                HttpStatus.OK);
    }

    @PostMapping("/{email}/newHotel")
    public ResponseEntity<Object> addHotel(@PathVariable String email,
                                           @RequestBody HotelDto hotelDto){

        hotelService.addHotel(hotelDto, email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added hotel");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{hotelId}/newRoom")
    public ResponseEntity<Object> addRoomToHotel(@PathVariable Long hotelId,
                                                 @RequestBody RoomDto roomDto){

        roomService.addRoom(hotelId, roomDto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added room");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/hotel/{hotelId}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long hotelId,
                                                @RequestBody ToUpdateHotelDto toUpdateHotelDto){

        BigDecimal _rate = new BigDecimal(toUpdateHotelDto.getRate());

        return new ResponseEntity<>(hotelService.updateHotel(hotelId,
                                    toUpdateHotelDto.getHotelName(),
                                    toUpdateHotelDto.getStreet(),
                                    toUpdateHotelDto.getCity(),
                                    toUpdateHotelDto.getCountry(),
                                    toUpdateHotelDto.getHotelDescription(),
                                    _rate), HttpStatus.OK);

    }

    @PatchMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long hotelId,
                                              @PathVariable Long roomId,
                                              @RequestBody RoomDto roomDto){

        return new ResponseEntity<>(roomService.updateRoom(hotelId, roomId, roomDto), HttpStatus.OK);
    }


    @DeleteMapping("/hotel/{hotelId}")
    public ResponseEntity<Object> deleteHotel(@PathVariable Long hotelId){

        hotelService.deleteHotel(hotelId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully deleted hotel");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Object> deleteRoomByHotelIdAndRoomId(@PathVariable Long hotelId,
                                                               @PathVariable Long roomId){

        hotelService.deleteRoomFromHotel(hotelId, roomId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully deleted room");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
