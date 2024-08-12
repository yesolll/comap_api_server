package com.yesolll.comap_api_server.domain.place.repository;

import com.yesolll.comap_api_server.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
