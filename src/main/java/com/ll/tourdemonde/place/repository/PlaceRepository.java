package com.ll.tourdemonde.place.repository;

import com.ll.tourdemonde.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByNameAndAddress(String name, String address);

    Optional<Place> findFirstByOrderByIdDesc();
}
