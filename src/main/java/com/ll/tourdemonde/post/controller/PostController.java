package com.ll.tourdemonde.post.controller;


import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.entity.PostPlaceReview;
import com.ll.tourdemonde.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String createPost(PostCreateForm postCreateForm) {

        return "post/post_create";
    }

    @PostMapping("/create")
    public String createPost(@Valid PostCreateForm postCreateForm, BindingResult bindingResult, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }
        postService.writePost(postCreateForm, member);
        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String showPostList(Model model) {
        List<Post> postList = postService.showPostList();
        model.addAttribute("postList", postList);
        return "post/post_list";
    }

    @GetMapping("/detail/{id}")
    public String showPostDetail(Model model, @PathVariable("id") Long id) {
        Post post = postService.getPost(id);
        List<PostPlaceReview> postPlaceReviewList = postService.getPostPlaceReview(id);
        model.addAttribute("post", post);
        model.addAttribute("postPlaceReviewList", postPlaceReviewList); // 속성 이름을 정확히 맞춥니다
        return "post/post_detail";
    }
}
