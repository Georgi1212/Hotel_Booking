package com.app.hotelbooking.service;

import com.app.hotelbooking.dto.BookingDto;
import com.app.hotelbooking.dto.OccupancyDto;
import com.app.hotelbooking.model.*;
import com.app.hotelbooking.repository.BookingRepository;
import com.app.hotelbooking.repository.OccupancyRepository;
import com.app.hotelbooking.validation.ObjectFoundException;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class BookingService {
    private final UserService userService;
    private final RoomService roomService;

    private final OccupancyService occupancyService;
    private final OccupancyRepository occupancyRepository;

    private final BookingRepository bookingRepository;

    private int getDaysBetween(final LocalDate checkIn, final LocalDate checkOut){
        return (int)ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    private boolean isLocalDateBetweenRange(LocalDate startDate, LocalDate endDate, LocalDate check){
        return nonNull(check) && (check.isEqual(startDate) || check.isAfter(startDate))
                && (check.isEqual(endDate) || check.isBefore(endDate));
    }

    private BookingDto getBookingDto(final Booking booking){   //, final Occupancy occupancy, final Room room){
        BookingDto bookingDto = new BookingDto();

        bookingDto.setUserEmail(booking.getUser().getEmail());
        bookingDto.setUserFirstName(booking.getUser().getFirstName());
        bookingDto.setUserLastName(booking.getUser().getLastName());
        bookingDto.setUserPhoneNumber(booking.getUser().getPhoneNumber());
        bookingDto.setUserAddress(booking.getUser().getAddress());

        bookingDto.setHostEmail(booking.getOccupancy().getRoom().getHotel().getHost().getEmail());
        bookingDto.setHostFirstName(booking.getOccupancy().getRoom().getHotel().getHost().getFirstName());
        bookingDto.setHostLastName(booking.getOccupancy().getRoom().getHotel().getHost().getLastName());
        bookingDto.setHostPhoneNumber(booking.getOccupancy().getRoom().getHotel().getHost().getPhoneNumber());
        bookingDto.setHostAddress(booking.getOccupancy().getRoom().getHotel().getHost().getAddress());

        bookingDto.setHotelId(booking.getOccupancy().getRoom().getHotel().getId());
        bookingDto.setHotelName(booking.getOccupancy().getRoom().getHotel().getHotelName());
        bookingDto.setHotelStreet(booking.getOccupancy().getRoom().getHotel().getStreet());
        bookingDto.setHotelCity(booking.getOccupancy().getRoom().getHotel().getCity());
        bookingDto.setHotelCountry(booking.getOccupancy().getRoom().getHotel().getCountry());

        bookingDto.setRoomId(booking.getOccupancy().getRoom().getId());
        bookingDto.setRoomPrice(booking.getOccupancy().getRoom().getRoomPrice());
        bookingDto.setRoomType(booking.getOccupancy().getRoom().getRoomSizeType().getRoomType().toString());
        bookingDto.setRoomCapacity(booking.getOccupancy().getRoom().getRoomSizeType().getRoomCapacity());
        bookingDto.setCheck_in(booking.getOccupancy().getCheck_in());
        bookingDto.setCheck_out(booking.getOccupancy().getCheck_out());
        bookingDto.setSumPrice(booking.getSumPrice());
        bookingDto.setCreatedAt(booking.getCreatedAt());

        return bookingDto;
    }

    public List<BookingDto> getBookingsByHotelIdAndRoomId(final Long hotelId, final Long roomId){
        Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        List<Occupancy> occupancies = occupancyService.findOccupanciesByRoom(room);

        List<BookingDto> bookingsDto = new ArrayList<>();

        for(Occupancy occupancy : occupancies){
            Booking booking = bookingRepository.findFirstByOccupancy(occupancy);
            BookingDto bookingDto = getBookingDto(booking);
            bookingsDto.add(bookingDto);
        }

        return bookingsDto;
    }

    public List<BookingDto> getBookingsByUserEmail(final String email){
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new ObjectFoundException("There is no such user"));

        List<Booking> userBookings = bookingRepository.findAllByUser(user);

        List<BookingDto> userBookingsDto = new ArrayList<>();

        for(Booking userBooking : userBookings){
            BookingDto userBookingDto = getBookingDto(userBooking);
            userBookingsDto.add(userBookingDto);
        }

        return userBookingsDto;
    }

    public List<BookingDto> getBookingsByHotelId(final Long hotelId){

        List<Room> rooms = roomService.getAllRoomEntitiesByHotelId(hotelId);

        List<BookingDto> allBookingsDto = new ArrayList<>();

        for(Room room : rooms){
            List<BookingDto> roomBookingsDto = getBookingsByHotelIdAndRoomId(hotelId, room.getId());
            allBookingsDto.addAll(roomBookingsDto);
        }

        return allBookingsDto;
    }

    public List<BookingDto> getBookingsByHotelIdAndRoomIdForTimePeriod(final Long hotelId, final Long roomId,
                                                                       final LocalDate startDate, final LocalDate endDate){
        if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("The check out date cannot be before the check in date");
        }

        List<BookingDto> bookings = getBookingsByHotelIdAndRoomId(hotelId, roomId);

        return bookings.stream()
                .filter(b -> (isLocalDateBetweenRange(startDate, endDate, b.getCheck_in()) &&
                             isLocalDateBetweenRange(startDate, endDate, b.getCheck_out())) &&
                             b.getCheck_in().isBefore(b.getCheck_out()))
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByHotelIdForTimePeriod(final Long hotelId, final LocalDate startDate, final LocalDate endDate){
        if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("The check out date cannot be before the check in date");
        }

        List<BookingDto> bookings = getBookingsByHotelId(hotelId);

        return bookings.stream()
                .filter(b -> (isLocalDateBetweenRange(startDate, endDate, b.getCheck_in()) &&
                        isLocalDateBetweenRange(startDate, endDate, b.getCheck_out())) &&
                        b.getCheck_in().isBefore(b.getCheck_out()))
                .collect(Collectors.toList());
    }

    public List<OccupancyDto> getOccupanciesByHotelIdAndRoomId(final Long hotelId, final Long roomId){
        Room room = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        List<Occupancy> occupancies = occupancyService.findOccupanciesByRoom(room);

        List<OccupancyDto> occupanciesDto = new ArrayList<>();

        for(Occupancy occupancy : occupancies){
            OccupancyDto occupancyDto = new OccupancyDto();

            occupancyDto.setHotelId(hotelId);
            occupancyDto.setRoomId(roomId);
            occupancyDto.setCheck_in(occupancy.getCheck_in());
            occupancyDto.setCheck_out(occupancy.getCheck_out());

            occupanciesDto.add(occupancyDto);
        }

        return occupanciesDto;
    }

    public List<OccupancyDto> getOccupanciesByHotelId(final Long hotelId){

        List<Room> rooms = roomService.getAllRoomEntitiesByHotelId(hotelId);

        List<OccupancyDto> allOccupanciesDto = new ArrayList<>();

        for(Room room : rooms){
            List<OccupancyDto> roomOccupanciesDto = getOccupanciesByHotelIdAndRoomId(hotelId, room.getId());
            allOccupanciesDto.addAll(roomOccupanciesDto);
        }

        return allOccupanciesDto;
    }

    public List<OccupancyDto> getOccupanciesByHotelIdAndRoomIdForTimePeriod(final Long hotelId, final Long roomId,
                                                                            final LocalDate startDate, final LocalDate endDate){
        if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("The check out date cannot be before the check in date");
        }

        List<OccupancyDto> occupanciesDto = getOccupanciesByHotelIdAndRoomId(hotelId, roomId);

        return occupanciesDto.stream()
                .filter(o -> (isLocalDateBetweenRange(startDate, endDate, o.getCheck_in()) &&
                        isLocalDateBetweenRange(startDate, endDate, o.getCheck_out())) &&
                        o.getCheck_in().isBefore(o.getCheck_out()))
                .collect(Collectors.toList());
    }

    public List<OccupancyDto> getOccupanciesByHotelIdForTimePeriod(final Long hotelId, final LocalDate startDate, final LocalDate endDate){
        if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("The check out date cannot be before the check in date");
        }

        List<OccupancyDto> occupanciesDto = getOccupanciesByHotelId(hotelId);

        return occupanciesDto.stream()
                .filter(o -> (isLocalDateBetweenRange(startDate, endDate, o.getCheck_in()) &&
                        isLocalDateBetweenRange(startDate, endDate, o.getCheck_out())) &&
                        o.getCheck_in().isBefore(o.getCheck_out()))
                .collect(Collectors.toList());
    }

    public void addBooking(final String email, final Long hotelId, final Long roomId,
                           final LocalDate checkIn, final LocalDate checkOut){

        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such user"));

        Room roomToBook = roomService.getRoomByHotelAndRoomId(hotelId, roomId);

        boolean isRoomOccupied = occupancyService.isRoomOccupied(roomToBook, checkIn, checkOut);

        if(isRoomOccupied){
            throw new IllegalArgumentException("The room you are trying to book is not free");
        }

        Occupancy occupancy = new Occupancy();
        occupancy.setCheck_in(checkIn);
        occupancy.setCheck_out(checkOut);
        occupancy.setRoom(roomToBook);

        roomToBook.getOccupancies().add(occupancy);

        Booking booking = new Booking();
        //booking.setRoom(roomToBook);
        booking.setOccupancy(occupancy);
        booking.setUser(user);

        BigDecimal daysBetween = new BigDecimal(getDaysBetween(checkIn, checkOut));
        BigDecimal roomPrice = roomToBook.getRoomPrice();

        booking.setSumPrice(daysBetween.multiply(roomPrice));

        //roomToBook.getBookings().add(booking);
        user.getBookings().add(booking);

        occupancyRepository.save(occupancy);
        bookingRepository.save(booking);
    }
}
