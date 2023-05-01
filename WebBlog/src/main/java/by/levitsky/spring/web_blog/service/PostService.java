package by.levitsky.spring.web_blog.service;

import by.levitsky.spring.web_blog.models.Post;

import java.util.Optional;

public interface PostService {
    void create(Post post);

    Iterable<Post> getAllPosts();

    Optional<Post> getPostById(long id);
    boolean updatePost(Post post, long id);

    boolean deletePost(long id);
}
