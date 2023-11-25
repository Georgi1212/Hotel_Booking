package com.app.hotelbooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_image")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "image_url")
    private String imageUrl;
}
