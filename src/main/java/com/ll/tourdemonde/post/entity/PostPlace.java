package com.ll.tourdemonde.post.entity;


import com.ll.tourdemonde.place.entity.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PostPlace extends BaseTime {

    @ManyToOne(fetch = LAZY)
    private Post post;
    @ManyToOne(fetch = LAZY)
    private Place place;

    @OneToOne(mappedBy = "postPlace", cascade = PERSIST)
    @Setter
    private PostPlacePlaceReview postPlacePlaceReview;
}
