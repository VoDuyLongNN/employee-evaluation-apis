package com.example.employeeevaluation.entity;

import com.example.employeeevaluation.entity.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @JsonBackReference
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "event_has_category", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "evt_category_id"))

    @JsonManagedReference
    private Set<EventCategory> categories = new HashSet<>();

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private EStatus status;
}


