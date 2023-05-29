package com.railway.finalProject.service;

import com.railway.finalProject.dto.StationDto;
import com.railway.finalProject.models.Stations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StationService {

    boolean saveStation(StationDto stationDto);

    Page<Stations> findAll(Pageable pageable);
    
    @Query(value = "select * from stations where station_name = ?1", nativeQuery = true)
    Stations findStationByName(String stationName);

    boolean existsById(Long id);

    Optional<Stations> findById(Long id);

    void delete(Stations station);

    boolean updateStation(StationDto stationDto);

    Iterable<Stations> findFullStationList();
}
