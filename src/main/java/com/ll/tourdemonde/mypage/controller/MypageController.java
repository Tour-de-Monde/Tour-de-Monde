package com.ll.tourdemonde.mypage.controller;

import com.ll.tourdemonde.member.dto.MemberCreateForm;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.mypage.service.MypageService;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MypageController {

    private final MypageService mypageService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model){
        Optional<Member> member = this.memberService.findByUsername(principal.getName());
        String username = memberService.findUsername(member.get());

        List<Post> myPostList = mypageService.myPostList(username);
        List<Post> votePostList = mypageService.votePostList(username);
<<<<<<< HEAD
        List<Order> myOrderList = mypageService.myOrderList(username);

        model.addAttribute("myPostList", myPostList); //현재 로그인한 사용자가 작성한 글 리스트 전달
        model.addAttribute("votePostList", votePostList); //현재 로그인한 사용자가 좋아요 한 글 리스트 전달
        model.addAttribute("myOrderList", myOrderList); //현재 로그인한 사용자의 예약 리스트 전달
=======

        model.addAttribute("myPostList", myPostList); //현재 로그인한 사용자가 작성한 글 리스트 전달
        model.addAttribute("votePostList", votePostList); //현재 로그인한 사용자가 좋아요 한 글 리스트 전달
>>>>>>> 26a5936 (feat : 마이페이지 좋아요 누른 글 리스트 구현)

        return "mypage/mypage";
    }
}