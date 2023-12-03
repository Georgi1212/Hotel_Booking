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

    //TODO: when I book a room, immediately I have to add a record in the occupancy table
    public boolean isRoomOccupied(final Room room, final LocalDate checkIn, final LocalDate checkOut){
        List<Occupancy> occupancyListForSpecificRoom = occupancyRepository.findOccupanciesByRoom(room);

        for(Occupancy occupancy : occupancyListForSpecificRoom){
            LocalDate occupancyCheckIn = occupancy.getCheck_in();
            LocalDate occupancyCheckOut = occupancy.getCheck_out();

            if( !(((checkIn.isBefore(occupancyCheckIn) && checkIn.isBefore(occupancyCheckOut)) &&
                            (checkOut.isBefore(occupancyCheckIn) || checkOut.isEqual(occupancyCheckIn)) &&
                            (checkOut.isBefore(occupancyCheckOut)))  ||

                    ((checkIn.isAfter(occupancyCheckIn) && (checkIn.isAfter(occupancyCheckOut) || checkIn.isEqual(occupancyCheckOut))) &&
                            (checkOut.isAfter(occupancyCheckIn) && checkOut.isAfter(occupancyCheckOut)))) ) {

                return true;
            }
        }

        return false;
    }
}
