package by.levitsky.spring.web_blog.repo;

import by.levitsky.spring.web_blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Long> {

}
