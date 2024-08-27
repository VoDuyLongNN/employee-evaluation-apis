package com.example.employeeevaluation.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_employee_id")
    private Long eventEmployeeId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "added_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate addedDate;
}
