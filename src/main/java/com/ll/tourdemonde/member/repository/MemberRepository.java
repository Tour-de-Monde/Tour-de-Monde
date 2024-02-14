package com.ll.tourdemonde.member.repository;

import com.ll.tourdemonde.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Page<Member> findAllByUsernameContaining(String kw, Pageable pageable);
}
