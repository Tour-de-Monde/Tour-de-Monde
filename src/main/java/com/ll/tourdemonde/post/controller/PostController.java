package com.ll.tourdemonde.post.controller;


import com.ll.tourdemonde.comment.dto.CommentCreateForm;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final PlaceService placeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createPost(PostCreateForm postCreateForm) {

        return "post/post_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute PostCreateForm postCreateForm, BindingResult bindingResult, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }

        postService.writePost(postCreateForm, member);

        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String showPostList(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = postService.getPostList(page);
        model.addAttribute("paging", paging);
        return "post/post_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String showPostDetail(Model model, @PathVariable("id") Long id, CommentCreateForm commentCreateForm) {
        Post post = postService.getPostWithViewCount(id);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String votePost(Principal principal, @PathVariable("id") Long id) {
        Post post = postService.getPost(id);
        Member member = memberService.getMember(principal.getName());
        postService.vote(post, member);
        return String.format("redirect:/post/detail/%s", id);
    }
}