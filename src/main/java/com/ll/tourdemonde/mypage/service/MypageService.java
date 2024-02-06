package com.ll.tourdemonde.mypage.service;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.repository.MemberRepository;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public List<Post> myPostList(String username) {
        List<Post> postList = postRepository.findAll();
        Optional<Member> member = memberRepository.findByUsername(username);
        Long id = member.get().getId();

        List<Post> myPostList = postList.stream()
                .filter(post -> post.getAuthor().getId().equals(id))
                .collect(Collectors.toList());

        return myPostList;
    }
}
