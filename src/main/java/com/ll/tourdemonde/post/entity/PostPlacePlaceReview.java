package com.ll.tourdemonde.post.entity;

import com.ll.tourdemonde.place.entity.PlaceReview;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPlacePlaceReview extends BaseTime {

    @OneToOne(fetch = LAZY)
    private PlaceReview placeReview;

    @OneToOne(fetch = LAZY)
    private PostPlace postPlace;
}
