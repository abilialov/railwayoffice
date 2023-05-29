package com.railway.finalProject.service.impl;

import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.models.Routes;
import com.railway.finalProject.repo.RoutesRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceImplTest {

    @MockBean
    private RoutesRepository routesRepository;

    @Autowired
    private RouteServiceImpl routeService;

    @Test
    public void existsRouteById() {
        Long id = 1L;
        Mockito.when(routesRepository.existsById(id)).thenReturn(true);

        boolean isRouteExist = routeService.existsRouteById(id);

        Assert.assertTrue(isRouteExist);
    }

    @Test
    public void findRouteById() {
        Long id = 1L;
        Routes routesMock = getRoutes().get(0);
        Mockito.when(routesRepository.findById(id)).thenReturn(Optional.ofNullable(routesMock));

        Optional<Routes> resultRoute = routeService.findRouteById(id);

        Assert.assertTrue(resultRoute.isPresent());
        Assert.assertEquals(resultRoute.get().getFree_places(), routesMock.getFree_places());
        Assert.assertEquals(resultRoute.get(), routesMock);

    }

    private List<Routes> getRoutes() {
        Routes route1 = new Routes();
        Routes route2 = new Routes();

        route1.setId(1L);
        route1.setFree_places(55);
        route1.setTicket_price(44);

        route2.setId(2L);
        route2.setFree_places(25);
        route2.setTicket_price(24);

        return List.of(route1, route2);
    }

    @Test
    public void updateRoute() {
        RouteDto routeDto = new RouteDto();
        routeDto.setId(1L);
        routeDto.setTimeDeparture("2010-10-10 10:10");
        routeDto.setTimeArrival("2010-10-11 10:10");
        routeDto.setTicketPrice("55");
        routeDto.setFreePlaces("55");
        routeDto.setStationIdDeparture(1);
        routeDto.setStationIdArive(2);

        boolean isRouteCreated = routeService.updateRoute(routeDto);
        Assert.assertTrue(isRouteCreated);
    }

    @Test
    public void routeSearch() {
        Page<Routes> routesFromMock = new PageImpl<>(getRoutes());
        PageRequest pageable = PageRequest.of(0, 2);
        RouteDto routeDto = new RouteDto();
        routeDto.setTimeDeparture("2010-10-10 10:10");
        routeDto.setStationIdArive(1);
        routeDto.setTimeArrival("2010-10-11 10:10");
        routeDto.setStationIdDeparture(2);
        Mockito.when(routesRepository.routeSearch(pageable,  routeDto.getStationIdDeparture(),
                routeDto.getStationIdArive(), routeDto.getTimeDeparture(), routeDto.getTimeArrival()))
                .thenReturn(routesFromMock);

        Page<Routes> resultRoutes = routeService.routeSearch(pageable, routeDto);

        Assert.assertNotNull(resultRoutes);
        Assert.assertEquals(resultRoutes.getTotalPages(), routesFromMock.getTotalPages());
        Assert.assertEquals(resultRoutes.getTotalElements(), resultRoutes.getTotalElements());
        Assert.assertEquals(resultRoutes, routesFromMock);
    }

    @Test
    public void saveRoute() {
        RouteDto routeDto = new RouteDto();
        routeDto.setTimeDeparture("2010-10-10 10:10");
        routeDto.setTimeArrival("2010-10-11 10:10");
        routeDto.setTicketPrice("55");
        routeDto.setFreePlaces("55");
        routeDto.setStationIdDeparture(1);
        routeDto.setStationIdArive(2);

        boolean isRouteCreated = routeService.saveRoute(routeDto);
        Assert.assertTrue(isRouteCreated);

    }
}