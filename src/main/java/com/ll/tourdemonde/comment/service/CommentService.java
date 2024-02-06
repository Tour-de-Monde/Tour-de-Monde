package com.ll.tourdemonde.comment.service;

import com.ll.tourdemonde.comment.entity.Comment;
import com.ll.tourdemonde.comment.repository.CommentRepository;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Post post, String content, Member member) {
        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .author(member)
                .build();
        commentRepository.save(comment);
    }

}
