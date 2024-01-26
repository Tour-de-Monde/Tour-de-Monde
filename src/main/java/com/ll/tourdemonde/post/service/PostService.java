package com.ll.tourdemonde.post.service;


import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.repository.MemberRepository;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.entity.PostPlaceReview;
import com.ll.tourdemonde.post.repository.PostPlaceReviewRepository;
import com.ll.tourdemonde.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final PostPlaceReviewRepository postPlaceReviewRepository;

    public void writePost(PostCreateForm postCreateForm) {
        // 멤버(저자) 찾기
//        Optional<Member> findAuthor = memberRepository.findById(postCreateForm.getAuthorId());
//        Member author = findAuthor.get();


        Member member1 = memberRepository.save(
                Member.builder()
                        .username("user1")
                        .password("1234")
                        .build()
        );

        // 게시글 생성
        Post post = Post.builder()
                .title(postCreateForm.getTitle())
                .category(postCreateForm.getCategory())
                .author(member1)
                .build();

        List<Place> places = createPlacesIfNotExist(postCreateForm.getPlaceNames(), postCreateForm.getCoordinates());
        placeRepository.saveAll(places);
        // 게시글에 장소 연결

        List<PostPlaceReview> postPlaceReviews = createPostPlaceReviews(post, postCreateForm.getRatings(), postCreateForm.getReviews(), places);

        // 게시글 저장 (PostPlace도 CascadeType.ALL 덕분에 함께 저장됨)
        postRepository.save(post);

        for (Place place : places) {
            post.addPlace(place);
        }

        postPlaceReviewRepository.saveAll(postPlaceReviews);
    }

    private List<Place> createPlacesIfNotExist(List<String> placeNames, List<String> coordinates) {
        List<Place> places = new ArrayList<>();

        for (int i = 0; i < coordinates.size(); i++) {
            String coordinate = coordinates.get(i);
            Optional<Place> existingPlace = placeRepository.findByCoordinate(coordinate);

            if (!existingPlace.isPresent()) {
                // 좌표가 존재하지 않으면 새로운 Place 객체 생성
                Place newPlace = Place.builder()
                        .name(placeNames.get(i))
                        .coordinate(coordinate)
                        .build();
                places.add(newPlace);
            }
        }
        return places;
    }

    private List<PostPlaceReview> createPostPlaceReviews(Post post, List<Integer> ratings, List<String> reviews, List<Place> places) {
        List<PostPlaceReview> postPlaceReviews = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            int rating = ratings.get(i);
            String review = reviews.get(i);

            PostPlaceReview postPlaceReview = PostPlaceReview.builder()
                    .post(post)
                    .place(place)
                    .rating(rating)
                    .review(review)
                    .build();

            postPlaceReviews.add(postPlaceReview);
        }
        return postPlaceReviews;
    }
}
