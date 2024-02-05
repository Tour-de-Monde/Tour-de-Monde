package com.ll.tourdemonde.mypage.controller;

import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class mypageController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/mypage")
    public String Mmypage(){
        return "mypage/mypage";
    }
}
