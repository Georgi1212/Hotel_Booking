package com.app.hotelbooking.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

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

    private String imageName;
    //@Lob
    //@Convert(converter= VarbinaryJdbcType.class)
    @Column(name = "image_url", length = Integer.MAX_VALUE)
    private byte[] imageUrl;
}
