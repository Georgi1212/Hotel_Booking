package com.app.hotelbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "street", nullable = false, unique = true)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "hotel_description")
    private String description;

    //@Lob
    //@Convert(converter= VarbinaryJdbcType.class)
    @Column(name = "hotel_image")
    private byte[] hotelImageUrl;

    @Column(name = "rate", nullable = false)
    @Min(value = 1, message = "Value must be at least 1")
    @Max(value = 10, message = "Value must be at most 5")
    private BigDecimal rate;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    private Set<Room> rooms;

}
