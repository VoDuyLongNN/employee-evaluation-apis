package com.example.employeeevaluation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluation_step")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long stepId;

    @Column(name = "step_name")
    private String stepName;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @JsonBackReference
    private Event event;

    @Column(name = "level")
    private int level;
}
