package com.app.hotelbooking.service;

import com.app.hotelbooking.model.Booking;
import com.app.hotelbooking.model.Occupancy;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.User;
import com.app.hotelbooking.repository.BookingRepository;
import com.app.hotelbooking.repository.OccupancyRepository;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    public void addBooking(final String email, final Long roomId, final LocalDate checkIn, final LocalDate checkOut){

        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("There is no such user"));

        Room roomToBook = roomService.getRoomByRoomId(roomId);

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
        booking.setRoom(roomToBook);
        booking.setUser(user);

        BigDecimal daysBetween = new BigDecimal(getDaysBetween(checkIn, checkOut));
        BigDecimal roomPrice = roomToBook.getRoomPrice();

        booking.setSumPrice(daysBetween.multiply(roomPrice));

        roomToBook.getBookings().add(booking);
        user.getBookings().add(booking);

        occupancyRepository.save(occupancy);
        bookingRepository.save(booking);
    }
}
