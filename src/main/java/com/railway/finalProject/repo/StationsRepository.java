package com.railway.finalProject.repo;


import com.railway.finalProject.models.Stations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationsRepository extends JpaRepository<Stations, Long> {

    @Query(value = "select * from stations where station_name = ?1", nativeQuery = true)
    Stations findStationByName(String stationName);

}
