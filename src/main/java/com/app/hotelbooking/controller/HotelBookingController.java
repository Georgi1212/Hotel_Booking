package com.app.hotelbooking.controller;

import com.app.hotelbooking.dto.*;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.service.BookingService;
import com.app.hotelbooking.service.HotelService;
import com.app.hotelbooking.service.RoomImageService;
import com.app.hotelbooking.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import static org.springframework.http.MediaType.*;

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
//TODO functionality for cancelling a reservation/booking?

    @GetMapping("/unique-countries")
    public ResponseEntity<List<String>> getAllUniqueCountries() {

        return new ResponseEntity<>(hotelService.getUniqueCountries(), HttpStatus.OK);
    }

    @GetMapping("/{country}/cities")
    public ResponseEntity<List<String>> getCitiesByCountry(@PathVariable String country){

        return new ResponseEntity<>(hotelService.getCitiesByCountry(country), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<HotelDto>> getAvailableHotels(@RequestParam String country,
                                                             @RequestParam(required = false) String city,
                                                             @RequestParam LocalDate checkIn,
                                                             @RequestParam LocalDate checkOut){
        try {
            List<HotelDto> availableHotels = hotelService.getAvailableHotels(country, city, checkIn, checkOut);
            return new ResponseEntity<>(availableHotels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping("/{hotelId}/rooms/{roomId}/occupancies")
    public ResponseEntity<List<OccupancyDto>> getOccupanciesByHotelIdAndRoomId(@PathVariable Long hotelId,
                                                                               @PathVariable Long roomId){
        return new ResponseEntity<>(bookingService.getOccupanciesByHotelIdAndRoomId(hotelId, roomId), HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/occupancies")
    public ResponseEntity<List<OccupancyDto>> getOccupanciesByHotelId(@PathVariable Long hotelId){
        return new ResponseEntity<>(bookingService.getOccupanciesByHotelId(hotelId), HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/rooms/{roomId}/occupancies/timePeriod")
    public ResponseEntity<List<OccupancyDto>> getOccupanciesByHotelIdAndRoomIdForTimePeriod(@PathVariable Long hotelId,
                                                                                            @PathVariable Long roomId,
                                                                                            @RequestParam LocalDate startDate,
                                                                                            @RequestParam LocalDate endDate){
        return new ResponseEntity<>(bookingService.
                getOccupanciesByHotelIdAndRoomIdForTimePeriod(hotelId, roomId, startDate, endDate),
                HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/occupancies/timePeriod")
    public ResponseEntity<List<OccupancyDto>> getOccupanciesByHotelIdForTimePeriod(@PathVariable Long hotelId,
                                                                                   @RequestParam LocalDate startDate,
                                                                                   @RequestParam LocalDate endDate){
        return new ResponseEntity<>(bookingService.
                getOccupanciesByHotelIdForTimePeriod(hotelId, startDate, endDate),
                HttpStatus.OK);
    }

    @GetMapping(value= "/{hotelId}/rooms/{roomId}/image/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getImageByRoomAndImageName(@PathVariable final Long hotelId,
                                                             @PathVariable final Long roomId,
                                                             @PathVariable final String imageName){
        byte[] imageData = roomImageService.getImageByRoomAndImageName(hotelId, roomId, imageName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))//APPLICATION_OCTET_STREAM_VALUE))
                .body(imageData);
    }

    @GetMapping(value="/{hotelId}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getHotelImage(@PathVariable Long hotelId){

        byte[] imageData = hotelService.getHotelImage(hotelId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }

    @PostMapping("/{email}/newHotel")
    public ResponseEntity<Object> addHotel(@PathVariable String email,
                                           @RequestBody HotelDto hotelDto){

        hotelService.addHotel(hotelDto, email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added hotel");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{hotelId}/newRoom")
    public ResponseEntity<Object> addRoomToHotel(@PathVariable Long hotelId,
                                                 @RequestBody RoomDto roomDto){

        roomService.addRoom(hotelId, roomDto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added room");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{email}/newBooking")
    public ResponseEntity<Object> addBooking(@PathVariable String email,
                                             @RequestBody ToCreateBookingDto toCreateBookingDto){

        bookingService.addBooking(email, toCreateBookingDto.getHotelId(),
                toCreateBookingDto.getRoomId(),
                toCreateBookingDto.getCheck_in(),
                toCreateBookingDto.getCheck_out());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added booking");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{hotelId}/rooms/{roomId}/newImage")
    public ResponseEntity<Object> addImage(@PathVariable Long hotelId,
                                           @PathVariable Long roomId,
                                           @RequestParam("imageUrl") MultipartFile imageFile) throws IOException {

        roomImageService.addImageToRoom(hotelId, roomId, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added image to room");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{hotelId}/newImage")
    public ResponseEntity<Object> addImageToHotel(@PathVariable Long hotelId,
                                                  @RequestParam("imageUrl") MultipartFile imageFile) throws IOException {

        hotelService.addImageToHotel(hotelId, imageFile);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully added image to hotel");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @PatchMapping("/{hotelId}/image")
    public ResponseEntity<byte[]> updateHotelImage(@PathVariable Long hotelId,
                                                   @RequestParam("imageUrl") MultipartFile imageFile) throws IOException, DataFormatException {

        byte[] imageData = hotelService.updateHotelImage(hotelId, imageFile);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))//APPLICATION_OCTET_STREAM_VALUE))
                .body(imageData);
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

    @DeleteMapping("/{hotelId}/rooms/{roomId}/image/{imageName}")
    public ResponseEntity<Object> deleteImageToRoom(@PathVariable Long hotelId,
                                                    @PathVariable Long roomId,
                                                    @PathVariable String imageName){

        roomImageService.deleteImageToRoom(hotelId, roomId, imageName);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully deleted image from a room");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
