package com.app.hotelbooking.service;

import com.app.hotelbooking.model.Occupancy;
import com.app.hotelbooking.model.Room;
import com.app.hotelbooking.repository.OccupancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OccupancyService {
    private final OccupancyRepository occupancyRepository;

    public List<Occupancy> findOccupanciesByRoom(final Room room){
        return occupancyRepository.findAllByRoom(room);
    }
    public boolean isRoomOccupied(final Room room, final LocalDate checkIn, final LocalDate checkOut){
        List<Occupancy> occupancyListForSpecificRoom = occupancyRepository.findAllByRoom(room);

        if(occupancyListForSpecificRoom.isEmpty()){
            return true;
        }

        for(Occupancy occupancy : occupancyListForSpecificRoom){
            LocalDate occupancyCheckIn = occupancy.getCheck_in();
            LocalDate occupancyCheckOut = occupancy.getCheck_out();

            if(checkIn.isBefore(occupancyCheckOut) && checkOut.isAfter(occupancyCheckIn)){

                return true;
            }
        }

        return false;
    }
}