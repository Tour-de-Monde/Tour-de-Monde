package com.ll.tourdemonde.post.entity;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseTime {

    private String title;
    private String category;
    @ManyToOne
    private Member author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @Builder.Default
    private List<PostPlace> postPlaces = new ArrayList<>();


    public void addPlace(Place place) {
        PostPlace postPlace = PostPlace.builder()
                .post(this)
                .place(place)
                .author(this.getAuthor())
                .build();
        postPlaces.add(postPlace);
    }
}
