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

<<<<<<< HEAD
    @PreAuthorize("isAuthenticated()")
=======
>>>>>>> c260145 (feat : 마이페이지 수정)
    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model){
        Optional<Member> member = this.memberService.findByUsername(principal.getName());
        String username = memberService.findUsername(member.get());

        List<Post> myPostList = mypageService.myPostList(username);
        List<Post> votePostList = mypageService.votePostList(username);
        List<Order> myOrderList = mypageService.myOrderList(username);

        model.addAttribute("myPostList", myPostList); //현재 로그인한 사용자가 작성한 글 리스트 전달
        model.addAttribute("votePostList", votePostList); //현재 로그인한 사용자가 좋아요 한 글 리스트 전달
        model.addAttribute("myOrderList", myOrderList); //현재 로그인한 사용자의 예약 리스트 전달

        return "mypage/mypage";
    }
<<<<<<< HEAD
}
=======

    @GetMapping("/membershipInfo")
    public String signup(MemberCreateForm memberCreateForm) {
        return "domain/member/signUp";
    }

    //회원가입
    @PostMapping("/membershipInfo")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        //int verificationCode; 회원가입 인증 번호

        if (bindingResult.hasErrors()) {
            return "domain/member/signUp";
        }

        //패스워드와 패스워드 확인이 일치하지 않는 경우
        if (!memberCreateForm.getPassword().equals(memberCreateForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordNotConfirm",
                    "패스워드 확인이 일치하지 않습니다.");
            return "domain/member/signUp";
        }

        // 아이디 또는 휴대폰 번호 중복 시 예외 처리
        try {
            memberService.createMember(memberCreateForm.getUsername(), memberCreateForm.getPassword(),
                    memberCreateForm.getEmail(), memberCreateForm.getMemberName(),
                    memberCreateForm.getBirthDate(), memberCreateForm.getPhoneNumber(), memberCreateForm.getNickname());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 가입된 사용자입니다.");

            return "domain/member/signUp";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());

            return "domain/member/signUp";
        }

        return "redirect:/";
    }

}
>>>>>>> 965c8ca (feat : 마이페이지 예약 목록)
