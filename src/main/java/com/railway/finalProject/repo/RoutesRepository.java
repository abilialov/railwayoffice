package com.railway.finalProject.repo;

import com.railway.finalProject.models.Routes;
import com.railway.finalProject.models.Stations;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutesRepository extends JpaRepository<Routes,Long> {

    @Query(value = "select * from routes where station_id_departure= ?1 and station_id_arive= ?2 and time_departure > ?3 and time_arrival < ?4" , nativeQuery = true)
    Page<Routes> routeSearch(Pageable pageable, int stationIdDeparture, int stationIdArrive, String departure_time, String arrival_time);

}
