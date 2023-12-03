package com.app.hotelbooking.repository;

import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findFirstById(final Long id);

    List<Room> findRoomsByHotel(final Hotel hotel);





}
