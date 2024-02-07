package com.ll.tourdemonde.post.service;


import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.entity.PlaceReview;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.post.dto.PostCreateForm;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PlaceService placeService;


    @Transactional
    public void writePost(PostCreateForm form, Member author) {

        // 게시글 생성
        Post post = Post.builder()
                .title(form.getTitle())
                .category(form.getCategory())
                .author(author)
                .build();

        // post ~~ place
        form.getPostPlaces().forEach(postPlaceDTO -> {
            Place place = placeService.findByCoordinateOrCreate(PlaceDto.builder()
                    .name(postPlaceDTO.getPlaceName())
                    .coordinate(postPlaceDTO.getCoordinate())
                    .build());
            PlaceReview placeReview = place.addReview(postPlaceDTO.getReview(), postPlaceDTO.getRating(), author);
            post.addPlace(place, placeReview);
        });

        postRepository.save(post);
    }

    public List<Post> showPostList() {
        return postRepository.findAll();
    }

    public Post getPostWithViewCount(Long id) {
        Optional<Post> opPost = postRepository.findById(id);
        if (opPost.isPresent()){
            Post post = opPost.get();
            post.increaseView();
            postRepository.save(post);
            return post;
        } else {
            throw new EntityNotFoundException("해당 게시물이 존재하지 않습니다.");
        }
    }

    public void vote(Post post, Member member) {
        if (post.getVoter().contains(member)){
            post.getVoter().remove(member);
        } else {
            post.getVoter().add(member);
        } postRepository.save(post);
    }

    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new EntityNotFoundException("해당 게시물이 존재하지 않습니다.");
        }
    }
}