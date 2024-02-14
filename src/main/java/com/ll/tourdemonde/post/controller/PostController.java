package com.ll.tourdemonde.post.controller;

import com.ll.tourdemonde.comment.dto.CommentCreateForm;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

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
    public String showPostList(Model model,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Post> paging;

        if (category != null && !category.isEmpty()) {
            // 카테고리가 선택된 경우 해당 카테고리의 게시물을 페이징하여 가져오기
            paging = postService.getPostsByCategory(category, page);
            model.addAttribute("paging", paging);
            model.addAttribute("category", category);
            return "post/post_list";
        } else {
            // 카테고리가 선택되지 않은 경우 기존 코드로 모든 게시물을 페이징하여 가져오기
            paging = postService.getPostList(page, kw);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
            return "post/post_list";
        }
    }
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deletePost(Principal principal, @PathVariable("id") Long id) {
        Post post = postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        postService.deletePost(post);
        return "redirect:/";
    }
}