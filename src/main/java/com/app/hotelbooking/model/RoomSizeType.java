package com.app.hotelbooking.model;

import com.app.hotelbooking.enums.RoomType;
import com.app.hotelbooking.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "room_size_type")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomSizeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "room_capacity", nullable = false)
    @Min(value = 1, message = "Value must be at least 1")
    @Max(value = 4, message = "Value must be at most 4")
    private int roomCapacity;

    @OneToMany(mappedBy = "roomSizeType", cascade = CascadeType.REMOVE)
    private Set<Room> rooms;

}
