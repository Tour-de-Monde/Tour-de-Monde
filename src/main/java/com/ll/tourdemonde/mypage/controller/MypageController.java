package com.ll.tourdemonde.mypage.controller;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.mypage.service.MypageService;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MypageController {

    private final MypageService mypageService;
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model){
        Optional<Member> member = this.memberService.findByUsername(principal.getName());
        String username = memberService.findUsername(member.get());

        List<Post> myPostList = mypageService.myPostList(username);
        List<Post> votePostList = mypageService.votePostList(username);

        model.addAttribute("myPostList", myPostList); //현재 로그인한 사용자가 작성한 글 리스트 전달
        model.addAttribute("votePostList", votePostList); //현재 로그인한 사용자가 좋아요 한 글 리스트 전달

        return "mypage/mypage";
    }
}
