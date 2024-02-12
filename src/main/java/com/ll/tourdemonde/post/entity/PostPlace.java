package com.ll.tourdemonde.post.entity;


import com.ll.tourdemonde.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.CascadeType.*;
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

    @OneToOne(mappedBy = "postPlace", cascade = REMOVE)
    @Setter
    private PostPlacePlaceReview postPlacePlaceReview;
}
