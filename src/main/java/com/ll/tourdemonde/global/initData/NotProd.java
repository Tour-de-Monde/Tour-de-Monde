package com.ll.tourdemonde.global.initData;

import com.ll.tourdemonde.domain.member.entity.Member;
import com.ll.tourdemonde.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;
    private final MemberService memberService;

    @Bean
    @org.springframework.core.annotation.Order(3)
    ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.findByUsername("admin").isPresent()) return;

        Member memberAdmin = memberService.join("admin", "1234", "관리자").getData();
        Member memberUser1 = memberService.join("user1", "1234", "유저1").getData();
        Member memberUser2 = memberService.join("user2", "1234", "유저2").getData();
        Member memberUser3 = memberService.join("user3", "1234", "유저3").getData();
    }
}