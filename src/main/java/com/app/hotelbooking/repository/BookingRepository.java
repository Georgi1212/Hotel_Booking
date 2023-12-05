package com.app.hotelbooking.repository;

import com.app.hotelbooking.model.Booking;
import com.app.hotelbooking.model.Occupancy;
import com.app.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findFirstByOccupancy(Occupancy occupancy);

    List<Booking> findAllByUser(User user);
}
