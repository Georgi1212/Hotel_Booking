package com.app.hotelbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "occupancy")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Occupancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "check_in", nullable = false)
    private LocalDate check_in;

    @Column(name = "check_out", nullable = false)
    private LocalDate check_out;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    @UpdateTimestamp
    private Timestamp deletedAt;

}
