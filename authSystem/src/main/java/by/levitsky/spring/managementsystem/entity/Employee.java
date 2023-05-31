package by.levitsky.spring.managementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "first_name", nullable = false)
    private String first_name;
    //@Column(name = "last_name", nullable = false)
    private String last_name;
    //@Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks = new ArrayList<Task>();
}
