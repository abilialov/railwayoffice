package com.railway.finalProject.service.impl;


import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.models.Routes;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.repo.RoutesRepository;
import com.railway.finalProject.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class RouteServiceImpl implements RouteService {

    private static final Logger LOGGER = LogManager.getLogger(RouteServiceImpl.class);

    @Autowired
    private RoutesRepository routesRepository;

    @Override
    public boolean existsRouteById(Long id) {
        return routesRepository.existsById(id);
    }

    @Override
    public Optional<Routes> findRouteById(Long id) {
        if(routesRepository.findById(id).isPresent()){
            LOGGER.info("Route by id = {} is exist", id);
            return routesRepository.findById(id);
        }
        LOGGER.error("No any route with this id = {}", id);
        return Optional.empty();
    }

    @Override
    public boolean updateRoute(RouteDto routeDto) {

        LOGGER.info("Update route id = {} info", routeDto.getId());

        Routes route = new Routes();
        route.setId(routeDto.getId());
        route.setStationDepartureInRoutes(new Stations(Long.valueOf(routeDto.getStationIdDeparture())));
        route.setStationArrivalInRoutes(new Stations(Long.valueOf(routeDto.getStationIdArive())));
        route.setTime_departure(routeDto.getTimeDeparture());
        route.setTime_arrival(routeDto.getTimeArrival());
        route.setTicket_price(Integer.parseInt(routeDto.getTicketPrice()));
        route.setFree_places(Integer.parseInt(routeDto.getFreePlaces()));

        routesRepository.save(route);
        return true;
    }

    @Override
    public Page<Routes> routeSearch(PageRequest pageable, RouteDto routeDto) {
        LOGGER.info("Searching a route from RouteId = {} to RouteId = {} ", routeDto.getStationIdDeparture(), routeDto.getStationIdArive());
        return routesRepository.routeSearch(pageable,  routeDto.getStationIdDeparture(),
                routeDto.getStationIdArive(), routeDto.getTimeDeparture(), routeDto.getTimeArrival());
    }

    @Override
    public boolean saveRoute(RouteDto routeDto) {

        LOGGER.info("Create new route");
        Routes route = new Routes();

        route.setTime_departure(routeDto.getTimeDeparture());
        route.setTime_arrival(routeDto.getTimeArrival());
        route.setTicket_price(Integer.parseInt(routeDto.getTicketPrice()));
        route.setFree_places(Integer.parseInt(routeDto.getFreePlaces()));
        route.setStationDepartureInRoutes(new Stations(Long.valueOf(routeDto.getStationIdDeparture())));
        route.setStationArrivalInRoutes(new Stations(Long.valueOf(routeDto.getStationIdArive())));

        routesRepository.save(route);
        return true;
    }

}
