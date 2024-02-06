package com.ll.tourdemonde.comment.entity;


import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.post.entity.BaseTime;
import com.ll.tourdemonde.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Member author;
}
