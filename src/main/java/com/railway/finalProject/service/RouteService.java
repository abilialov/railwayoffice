package com.railway.finalProject.service;

import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.models.Routes;
import com.railway.finalProject.models.Stations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RouteService {

    boolean existsRouteById(Long id);

    Optional<Routes> findRouteById(Long id);

    boolean updateRoute(RouteDto routeDto);

    Page<Routes> routeSearch(PageRequest of, RouteDto routeDto);

    boolean saveRoute(RouteDto routeDto);

}
