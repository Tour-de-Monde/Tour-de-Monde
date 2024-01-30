package com.ll.tourdemonde.domain.member.repository;

import com.ll.tourdemonde.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Page<Member> findAllByUsernameContaining(String kw, Pageable pageable);
}
