package com.ll.tourdemonde.post.repository;

import com.ll.tourdemonde.post.entity.PostPlaceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPlaceReviewRepository extends JpaRepository<PostPlaceReview, Long> {
    List<PostPlaceReview> findByPostId(Long postId);
}
