package by.levitsky.spring.web_blog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Title shouldn't be empty!")
    @Size(min = 2, max = 64, message = "Between 2 and 64 characters!")
    private String title;
    @NotEmpty(message = "Preview shouldn't be empty!")
    @Size(min = 2, max = 64, message = "Between 2 and 64 characters!")
    private String anons;
    @NotEmpty(message = "Text shouldn't be empty!")
    @Size(min = 2, max = 2000, message = "Between 2 and 2000 characters!")
    private String full_text;
    private int views;

    public Post(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }
}
