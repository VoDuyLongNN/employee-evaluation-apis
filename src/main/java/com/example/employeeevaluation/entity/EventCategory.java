package com.example.employeeevaluation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evt_category_id")
    private Long evtCategoryId;

    @Column(name = "category_name")
    private String categoryName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @JsonBackReference
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "event_has_category", joinColumns = @JoinColumn(name = "evt_category_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    @JsonManagedReference
    private Set<Event> events = new HashSet<>();
}
