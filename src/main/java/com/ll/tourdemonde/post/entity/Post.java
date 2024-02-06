package com.ll.tourdemonde.post.entity;

import com.ll.tourdemonde.comment.entity.Comment;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.entity.PlaceReview;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String category;
    private Long view;
    @ManyToOne
    private Member author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @Builder.Default
    private List<PostPlace> postPlaces = new ArrayList<>();
    @ManyToMany
    Set<Member> voter;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public void addPlace(Place place, PlaceReview review) {
        PostPlace postPlace = PostPlace.builder()
                .post(this)
                .place(place)
                .build();

        PostPlacePlaceReview postPlacePlaceReview = PostPlacePlaceReview.builder()
                .placeReview(review)
                .postPlace(postPlace)
                .build();

        postPlace.setPostPlacePlaceReview(postPlacePlaceReview);

        postPlaces.add(postPlace);
    }

    public void increaseView() {
        this.view++;
    }
}
