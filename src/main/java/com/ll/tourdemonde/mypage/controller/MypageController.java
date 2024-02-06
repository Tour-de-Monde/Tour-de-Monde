package com.ll.tourdemonde.mypage.controller;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.mypage.service.MypageService;
<<<<<<< HEAD
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
=======
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> 8a294b5 (feat : 마이페이지 내가 쓴 글 목록 구현)

import java.security.Principal;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
@RequestMapping("/member")
=======
>>>>>>> 8a294b5 (feat : 마이페이지 내가 쓴 글 목록 구현)
@RequiredArgsConstructor
@Controller
public class MypageController {

    private final MypageService mypageService;
    private final MemberService memberService;
<<<<<<< HEAD

    @PreAuthorize("isAuthenticated()")
=======
    private final PostService postService;

>>>>>>> 8a294b5 (feat : 마이페이지 내가 쓴 글 목록 구현)
    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model){
        Optional<Member> member = this.memberService.findByUsername(principal.getName());
        String username = memberService.findUsername(member.get());
<<<<<<< HEAD

        List<Post> myPostList = mypageService.myPostList(username);
        List<Post> votePostList = mypageService.votePostList(username);
        List<Order> myOrderList = mypageService.myOrderList(username);

        model.addAttribute("myPostList", myPostList); //현재 로그인한 사용자가 작성한 글 리스트 전달
        model.addAttribute("votePostList", votePostList); //현재 로그인한 사용자가 좋아요 한 글 리스트 전달
        model.addAttribute("myOrderList", myOrderList); //현재 로그인한 사용자의 예약 리스트 전달

        return "mypage/mypage";
    }
}
=======
        List<Post> myPostList = mypageService.myPostList(username);

        model.addAttribute("myPostList", myPostList);

        return "mypage/mypage";
    }
}
>>>>>>> 8a294b5 (feat : 마이페이지 내가 쓴 글 목록 구현)
