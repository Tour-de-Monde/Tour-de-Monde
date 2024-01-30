package com.ll.tourdemonde.post.entity;


import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.place.entity.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PostPlaceReview extends BaseTime{

    @ManyToOne
    private Member author;
    @ManyToOne
    private Post post;
    @ManyToOne
    private Place place;
    @OneToOne
    private PostPlace postPlace;
    private int rating;
    private String review;

}
