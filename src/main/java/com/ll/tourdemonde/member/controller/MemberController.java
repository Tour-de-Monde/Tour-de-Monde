package com.ll.tourdemonde.member.controller;

import com.ll.tourdemonde.mail.service.MailService;
import com.ll.tourdemonde.member.dto.MemberCreateForm;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    //회원가입
    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {
        return "domain/member/signUp";
    }

    //회원가입
    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        int verificationCode;

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

    //로그인
    @GetMapping("/signin")
    public String signin(){
        return "domain/member/signIn";
    }

    //메일로 아이디 찾기
    @GetMapping("/findid")
    public String findId(){
        return "domain/member/findId";
    }

    //메일로 아이디 찾기
    @PostMapping("/findid")
    public String findId(@RequestParam(value = "email") String email, Model model) {
        Optional<Member> member = memberService.findMemberByEmail(email);

        //입력한 메일의 가입 정보가 존재하지 않는 경우
        if (member.isEmpty()) {
            model.addAttribute("error", "가입되지 않은 메일입니다.");
            return "domain/member/findId";
        }

        String username = memberService.findUsername(member.get());
        mailService.sendUsernameMail(email, username);

        return "redirect:/member/signin";
    }

    //아이디, 메일을 통해 비밀번호 재발급
    @GetMapping("/renewpassword")
    public String renewPassword(){
        return "domain/member/renewPassword";
    }

    //아이디, 메일을 통해 비밀번호 재발급
    @PostMapping("/renewpassword")
    public String renewPassword(@RequestParam(value = "username") String username,
                                @RequestParam(value = "email") String email,
                                Model model){
        Optional<Member> member = memberService.findMemberByEmail(email);
        String _username;
        String newPassword;

        //입력한 메일의 가입 정보가 존재하지 않는 경우
        if (member.isEmpty()) {
            model.addAttribute("error", "아이디와 이메일을 확인해주세요.");
            return "domain/member/renewpassword";
        }

        _username = memberService.findUsername(member.get());

        //입력한 메일의 가입 정보가 존재하지만 아이디가 일치하지 않는 경우
        if (!username.equals(_username)){
            model.addAttribute("error", "아이디와 이메일을 확인해주세요.");
            return "domain/member/renewpassword";
        }

        newPassword = mailService.sendNewPassword(email);
        memberService.renewPassword(member.get(), newPassword);

        return "redirect:/member/signin";
    }
}