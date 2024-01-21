package com.ll.tourdemonde.place.repository;

import com.ll.tourdemonde.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
