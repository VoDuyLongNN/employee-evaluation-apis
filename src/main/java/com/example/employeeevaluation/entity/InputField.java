package com.example.employeeevaluation.entity;

import com.example.employeeevaluation.entity.enums.EInputPermission;
import com.example.employeeevaluation.entity.enums.EInputType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity(name = "input_field")
public class InputField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_field_id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private EInputType type;

    @Column(name = "permission")
    @Enumerated(value = EnumType.STRING)
    private EInputPermission permission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "step_id", referencedColumnName = "step_id")
    private EvaluationStep evaluationStep;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
