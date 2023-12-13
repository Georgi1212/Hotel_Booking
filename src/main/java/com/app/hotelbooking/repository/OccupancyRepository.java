package com.app.hotelbooking.repository;

import com.app.hotelbooking.model.Occupancy;
import com.app.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {
    List<Occupancy> findAllByRoom(Room room);
}
