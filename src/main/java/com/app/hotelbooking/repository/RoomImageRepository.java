package com.app.hotelbooking.repository;

import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.model.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

    Optional<RoomImage> findFirstById(final Long id);
    Optional<RoomImage> findFirstByImageUrl(byte[] imageUrl);
    List<RoomImage> findAllByRoom(final Room room);
}
