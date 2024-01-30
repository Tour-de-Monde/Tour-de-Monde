package com.ll.tourdemonde.post.repository;

import com.ll.tourdemonde.post.entity.PostPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPlaceRepository extends JpaRepository<PostPlace, Long> {
}
