package com.ll.tourdemonde.post.controller;


<<<<<<< HEAD
import com.ll.tourdemonde.comment.dto.CommentCreateForm;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.place.service.PlaceService;
=======
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
>>>>>>> bff8c11 (Feat: 게시글 작성자 추가)
import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
<<<<<<< HEAD
    private final PlaceService placeService;
=======
>>>>>>> bff8c11 (Feat: 게시글 작성자 추가)

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createPost(PostCreateForm postCreateForm) {

        return "post/post_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
<<<<<<< HEAD
    public String createPost(@Valid @ModelAttribute PostCreateForm postCreateForm, BindingResult bindingResult, Principal principal) {
=======
    public String createPost(@Valid PostCreateForm postCreateForm, BindingResult bindingResult, Principal principal) {
>>>>>>> bff8c11 (Feat: 게시글 작성자 추가)
        Member member = memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }
<<<<<<< HEAD

        postService.writePost(postCreateForm, member);

=======
        postService.writePost(postCreateForm, member);
>>>>>>> bff8c11 (Feat: 게시글 작성자 추가)
        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String showPostList(Model model) {
        List<Post> postList = postService.showPostList();
        model.addAttribute("postList", postList);
        return "post/post_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String showPostDetail(Model model, @PathVariable("id") Long id, CommentCreateForm commentCreateForm) {
        Post post = postService.getPostWithViewCount(id);
        model.addAttribute("post", post);
<<<<<<< HEAD
=======
        model.addAttribute("postPlaceReviewList", postPlaceReviewList);
>>>>>>> d98e20b (Refact: 게시물 저장 시 인덱스 문제 해결 및 게시물 상세 페이지 수정)
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