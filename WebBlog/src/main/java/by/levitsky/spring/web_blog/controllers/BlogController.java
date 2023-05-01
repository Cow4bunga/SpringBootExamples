package by.levitsky.spring.web_blog.controllers;

import by.levitsky.spring.web_blog.models.Post;
import by.levitsky.spring.web_blog.repo.PostRepository;
import by.levitsky.spring.web_blog.service.PostService;
import by.levitsky.spring.web_blog.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController { // to relocate some business logic into dao class
    @Autowired
    private PostService postService;

    @GetMapping("")// blog by default
    public String getBlogMain(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
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
        postService.create(new Post(title, anons, full_text));
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String getPostByID(@PathVariable(value = "id") long id, Model model) {
        if(postService.getPostById(id).isEmpty()){
            return "redirect:/blog";
        }

        model.addAttribute("post", postService.getPostById(id).get());
        return "blog-details";
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable("id") long id, Model model) {
        if(postService.getPostById(id).isEmpty()){
            return "redirect:/blog";
        }

        model.addAttribute("post", postService.getPostById(id).get());

        return "blog-edit";
    }

    @PostMapping("/{id}/edit")
    public String postBlogEdit(@RequestParam(required = false, name = "title") String title,
                               @RequestParam(required = false, name = "anons") String anons,
                               @RequestParam(required = false, name = "full_text") String full_text,
                               @PathVariable(value = "id") long id,
                               Model model) {
        postService.updatePost(new Post(title,anons,full_text),id);
        return "redirect:/blog";
    }

    @GetMapping("/{id}/remove")
    public String removePost(@PathVariable("id") long id, Model model) {
        if(postService.getPostById(id).isEmpty()){
            return "redirect:/blog";
        }

        model.addAttribute("post", postService.getPostById(id).get());

        return "blog-remove";
    }

    @PostMapping("/{id}/remove")
    public String deleteByID(@PathVariable(value = "id") long id, Model model) {
        postService.deletePost(id);
        return "redirect:/blog";
    }
}
