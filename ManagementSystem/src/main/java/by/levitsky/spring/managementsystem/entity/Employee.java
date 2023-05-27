package by.levitsky.spring.managementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Column(name = "last_name", nullable = false)
    private String last_name;
    @Column(name = "email")
    private String email;

}
