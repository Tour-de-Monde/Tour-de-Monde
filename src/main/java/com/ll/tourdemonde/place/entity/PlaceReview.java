package com.ll.tourdemonde.place.entity;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.post.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceReview extends BaseTime {

    @ManyToOne(fetch = LAZY)
    private Place place;

    @ManyToOne(fetch = LAZY)
    private Member author;

    private String rating;
    private String review;
}
