package com.ll.tourdemonde.post.controller;


import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    public String createPost(PostCreateForm postCreateForm) {

        return "post/post_create";
    }

    @PostMapping("/create")
    public String createPost(@Valid PostCreateForm postCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }

        postService.writePost(postCreateForm);
        return "redirect:/post/list";
    }
}
