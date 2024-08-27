package com.example.employeeevaluation.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "token_string")
    private String tokenString;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expire")
    private Date expire;
}
