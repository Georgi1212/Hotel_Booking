package com.app.hotelbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "room_price", nullable = false)
    private BigDecimal roomPrice;

    @ManyToOne
    @JoinColumn(name = "room_type", nullable = false)
    private RoomSizeType roomSizeType;

    @Column(name = "room_description")
    private String description;

    @Column(name = "num_children", nullable = false)
    private int numChildren;

    @Column(name = "num_adults", nullable = false)
    private int numAdults;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Occupancy> occupancies;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Set<RoomImage> roomImages;

    /*@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Set<Booking> bookings;*/
}
