package com.example.employeeevaluation.entity;

import com.example.employeeevaluation.entity.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    @Enumerated(value = EnumType.STRING)
    private ERole roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
