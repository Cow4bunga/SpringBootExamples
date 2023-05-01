package by.levitsky.spring.web_blog.service;

import by.levitsky.spring.web_blog.models.Post;
import by.levitsky.spring.web_blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void create(Post post) {
        postRepository.save(post);
    }

    @Override
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(long id) {
        Optional<Post> post=postRepository.findById(id);
        return post;
    }

    @Override
    public boolean updatePost(Post post, long id) {
        if(postRepository.existsById(id)){
            Post newPost=postRepository.findById(id).get();

            newPost.setTitle(post.getTitle());
            newPost.setAnons(post.getAnons());
            newPost.setFull_text(post.getFull_text());
            postRepository.save(newPost);

            return true;
        }

        return false;
    }

    @Override
    public boolean deletePost(long id) {
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
