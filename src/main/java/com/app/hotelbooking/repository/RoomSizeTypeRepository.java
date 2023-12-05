package com.app.hotelbooking.repository;

import com.app.hotelbooking.enums.RoomType;
import com.app.hotelbooking.model.RoomSizeType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomSizeTypeRepository extends JpaRepository<RoomSizeType, Long> {

    Optional<RoomSizeType> findFirstByRoomTypeAndRoomCapacity(RoomType roomType, @Min(value = 1, message = "Value must be at least 1") @Max(value = 4, message = "Value must be at most 4") int roomCapacity);
}
