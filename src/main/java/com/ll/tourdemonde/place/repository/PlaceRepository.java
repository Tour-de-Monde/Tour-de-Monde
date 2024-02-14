package com.ll.tourdemonde.place.repository;

import com.ll.tourdemonde.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByCoordinate(String coordinate);

    List<Place> findAllByOrderByNameAscCreateDateAsc();
}
