package com.ll.tourdemonde.comment.service;

import com.ll.tourdemonde.comment.entity.Comment;
import com.ll.tourdemonde.comment.repository.CommentRepository;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void createComment(Post post, String content, Member member) {
        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .author(member)
                .build();
        commentRepository.save(comment);
    }

    public Comment getComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new RuntimeException("Comment Not Found");
        }
    }

    public void modifyComment(Comment comment, String content) {
        Comment modifiedComment = Comment.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(content)
                .modifyDate(LocalDateTime.now())
                .post(comment.getPost())
                .build();
        commentRepository.save(modifiedComment);
    }

}
