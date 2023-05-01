package by.levitsky.spring.web_blog.controllers;

import by.levitsky.spring.web_blog.models.Post;
import by.levitsky.spring.web_blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController { // to relocate some business logic into dao class

    @Autowired
    private PostRepository repository;

    @GetMapping("")// blog by default
    public String getBlogMain(Model model) {
        Iterable<Post> posts = repository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/add")
    public String getBlogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/add")
    public String postBlogAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text,
                              Model model) {
        Post post = new Post(title, anons, full_text);
        repository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String getPostByID(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = repository.findById(id);
        if (post.isEmpty())
            return "redirect:/blog";
        model.addAttribute("post", post.get());
        return "blog-details";
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable("id") long id, Model model) {
        Optional<Post> post = repository.findById(id);

        if (post.isEmpty())
            return "redirect:/blog";

        model.addAttribute("post", post.get());

        return "blog-edit";
    }

    @PostMapping("/{id}/edit")
    public String postBlogEdit(@RequestParam(required = false, name="title") String title,
                               @RequestParam(required = false, name="anons") String anons,
                               @RequestParam(required = false, name = "full_text") String full_text,
                               @PathVariable(value = "id") long id,
                               Model model) {
        Optional<Post> post = repository.findById(id);

        Post postToUpd = post.get();
        postToUpd.setTitle(title);
        postToUpd.setAnons(anons);
        postToUpd.setFull_text(full_text);
        repository.save(postToUpd);

        return "redirect:/blog";
    }


    @PostMapping("/{id}/remove")
    public String deleteByID(@PathVariable(value = "id") long id, Model model) {
        System.out.println("ABOBA");
        Optional<Post> post = repository.findById(id);
        Post postToDel = post.get();

        repository.delete(postToDel);

        System.out.println("ABOBA2");
        return "redirect:/blog";
    }

}
