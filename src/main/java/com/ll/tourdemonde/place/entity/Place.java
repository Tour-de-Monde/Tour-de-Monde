package com.ll.tourdemonde.place.entity;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.post.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Place extends BaseTime {

    private String name;
    private String coordinate;

    @OneToMany(mappedBy = "place", cascade = PERSIST)
    @Builder.Default
    List<PlaceReview> placeReviews = new ArrayList<>();

    public PlaceReview addReview(String review, String rating, Member author) {
        PlaceReview placeReview = PlaceReview.builder()
                .review(review)
                .rating(rating)
                .author(author)
                .place(this)
                .build();
        this.placeReviews.add(placeReview);
        return placeReview;
    }
}
