package com.ll.tourdemonde.post.repository;

import com.ll.tourdemonde.post.entity.PostPlaceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPlaceReviewRepository extends JpaRepository<PostPlaceReview, Long> {
}
