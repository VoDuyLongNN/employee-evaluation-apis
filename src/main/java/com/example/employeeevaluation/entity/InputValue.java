package com.example.employeeevaluation.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity
@Table(name = "input_value", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"input_field_id", "user_id"})
})
public class InputValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_value_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "input_field_id")
    private InputField inputField;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "value")
    private String value;
}
