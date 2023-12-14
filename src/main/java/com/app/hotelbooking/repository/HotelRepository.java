package com.app.hotelbooking.repository;

import com.app.hotelbooking.model.Hotel;
import com.app.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findFirstByHotelNameAndCountryAndCity(final String hotelName, final String country, final String city);
    Optional<Hotel> findFirstById(final Long id);
    List<Hotel> findHotelsByCountry(final String country);
    List<Hotel> findHotelsByCountryAndCity(final String country, final String city);
    Optional<Hotel> findFirstByCountryAndCityAndStreet(final String country, final String city, final String street);
    List<Hotel> findHotelsByHost(final User user);

    @Query("SELECT DISTINCT h.country FROM Hotel h")
    List<String> findAllUniqueCountries();

    @Query("SELECT DISTINCT h.city FROM Hotel h WHERE h.country = :country")
    List<String> findAllCitiesByCountry(@Param("country") String country);
}
