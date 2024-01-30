package com.ll.tourdemonde.member.service;

import com.ll.tourdemonde.member.repository.MemberRepository;
import com.ll.tourdemonde.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(String username, String password,
                               String email, String memberName,
                               LocalDate birthDate, String phoneNumber){
        Member member = new Member();

        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password)); // 비밀번호를 암호화 하여 DB에 저장
        member.setEmail(email);
        member.setMemberName(memberName);
        member.setBirthDate(birthDate);
        member.setPhoneNumber(phoneNumber);

        this.memberRepository.save(member);
        return member;
    }

    //메일을 받아 일치하는 회원 찾기. 찾은 결과는 있을 수도, 없을 수도 있음
    public Optional<Member> findMemberByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        return member;
    }

    //멤버 객체로부터 아이디 반환
    public String findUsername(Member member){
        String username = member.getUsername();
        return username;
    }

    public Member renewPassword(Member member, String newPassword){
        member.setPassword(passwordEncoder.encode(newPassword)); // 새로 발급받은 비밀번호를 암호화 하여 DB에 저장

        this.memberRepository.save(member);
        return member;
    }
}