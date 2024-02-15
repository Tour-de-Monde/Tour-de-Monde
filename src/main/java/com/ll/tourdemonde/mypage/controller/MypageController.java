package com.ll.tourdemonde.mypage.controller;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.mypage.service.MypageService;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MypageController {

    private final MypageService mypageService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model,
                         @RequestParam(value="myPostPage", defaultValue="0") int myPostPage,
                         @RequestParam(value="myVotePage", defaultValue="0") int myVotePage,
                         @RequestParam(value="myOrderPage", defaultValue="0") int myOrderPage){
        Optional<Member> member = this.memberService.findByUsername(principal.getName());
        String username = memberService.findUsername(member.get());

        Page<Post> myPostList = mypageService.myPostList(username, myPostPage);
        Page<Post> votePostList = mypageService.votePostList(username, myVotePage);
        Page<Order> myOrderList = mypageService.myOrderList(username, myOrderPage);

        model.addAttribute("myPostList", myPostList); //현재 로그인한 사용자가 작성한 글 리스트 전달
        model.addAttribute("votePostList", votePostList); //현재 로그인한 사용자가 좋아요 한 글 리스트 전달
        model.addAttribute("myOrderList", myOrderList); //현재 로그인한 사용자의 예약 리스트 전달

        return "mypage/mypage";
    }
}