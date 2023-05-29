package com.railway.finalProject.utils;

import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.models.Routes;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.repo.RoutesRepository;
import com.railway.finalProject.service.RouteService;
import com.railway.finalProject.service.StationService;
import com.railway.finalProject.service.impl.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class RouteUtil {

    private static final Logger LOGGER = LogManager.getLogger(RouteUtil.class);

    @Autowired
    private StationService stationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RoutesRepository routesRepository;

    public String createNewRouteForm(Model model) {
        Iterable<Stations> stations = stationService.findFullStationList();
        model.addAttribute("list_of_stations",stations);

        RouteDto routeDto = new RouteDto();
        routeDto.setStationIdArive(0);
        routeDto.setStationIdDeparture(0);

        model.addAttribute("route", routeDto);
        LOGGER.debug("Upload page with creating route form");
        return "routesPage";
    }

    public String createRoute(RouteDto routeDto, BindingResult result, Model model) {

        if(result.hasErrors()){
            LOGGER.warn("Route does not created becouse form not validated");
            Iterable<Stations> stations = stationService.findFullStationList();
            model.addAttribute("list_of_stations",stations);
            model.addAttribute("route", routeDto);
            return "routesPage";
        }

        routeService.saveRoute(routeDto);
        LOGGER.info("Route from station id = {} to station id ={} created successfully",
                routeDto.getStationIdDeparture(), routeDto.getStationIdArive());
        return "redirect:/routes?success";
    }

    public String updateRoute(Long id, Model model) {

        LOGGER.info("Update route with id = {}", id);

        if(!routeService.existsRouteById(id)){
            LOGGER.warn("Route with id = {} not exist", id);
            return "redirect:/";
        }

        Iterable<Stations> stations = stationService.findFullStationList();
        model.addAttribute("list_of_stations",stations);

        /** find route by id */
        Optional<Routes> routeById = routeService.findRouteById(id);
        ArrayList<Routes> result = new ArrayList<>();
        routeById.ifPresent(result::add);

        RouteDto routeDto = new RouteDto();
        routeDto.setStationIdDeparture(Math.toIntExact(result.get(0).getStationDepartureInRoutes().getId()));
        routeDto.setStationIdArive(Math.toIntExact(result.get(0).getStationArrivalInRoutes().getId()));
        routeDto.setTimeDeparture(result.get(0).getTime_departure());
        routeDto.setTimeArrival(result.get(0).getTime_arrival());
        routeDto.setTicketPrice(String.valueOf(result.get(0).getTicket_price()));
        routeDto.setFreePlaces(String.valueOf(result.get(0).getFree_places()));
        routeDto.setId(result.get(0).getId());

        model.addAttribute("route", routeDto);
        LOGGER.info("Page with update route id = {} open successfully", id);
        return "routeUpdate";
    }

    public String updateRouteInfo(RouteDto routeDto, BindingResult result, Model model) {

        LOGGER.info("Updating route with id = {}", routeDto.getId());

        if(result.hasErrors()){
            LOGGER.info("Form with updating route info has validation troubles, route id = {}", routeDto.getId());
            Iterable<Stations> stations = stationService.findFullStationList();
            model.addAttribute("list_of_stations",stations);
            model.addAttribute("route", routeDto);
            return "routeUpdate";
        }

        routeService.updateRoute(routeDto);
        LOGGER.info("Route id = {} updated successfully", routeDto.getId());
        return "redirect:/?success";
    }

    public String findRoute(Integer page, RouteDto routeDto, BindingResult result, Model model) {

        LOGGER.info("Find list of routes");

        model.addAttribute("route", routeDto);
        Iterable<Stations> stations = stationService.findFullStationList();
        model.addAttribute("list_of_stations",stations);

        if ((routeDto.getStationIdDeparture() == 0) || (routeDto.getStationIdArive() == 0)) {
            model.addAttribute("stationnames", "stationnames");
            LOGGER.warn("Station id departure = {}, and station id arrival = {} should be not zero",
                    routeDto.getStationIdDeparture(),routeDto.getStationIdArive());
            return "homePage";
        }

        if ((routeDto.getTimeDeparture() == "") || (routeDto.getTimeArrival() == "")) {
            model.addAttribute("datevalues", "datevalues");
            LOGGER.warn("Time departure and time arrival should be not empty, now timedep = {} and timearrival ={}",
                    routeDto.getTimeDeparture(), routeDto.getTimeArrival());
            return "homePage";
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date dateNow = null;
        Date dateDeparture = null;
        Date dateArrival = null;

        try {
            dateNow = sdf.parse(currentDate);
            dateDeparture = sdf.parse(routeDto.getTimeDeparture().replace('T', ' '));
            dateArrival = sdf.parse(routeDto.getTimeArrival().replace('T', ' '));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dateNow.compareTo(dateDeparture) > 0){
            LOGGER.warn("Departure time should be bigger than current time");
            routeDto.setTimeDeparture(currentDate);
            model.addAttribute("depaturetime", "depaturetime");
            return "homePage";
        }

        if(dateDeparture.compareTo(dateArrival) > 0) {
            LOGGER.warn("Arrival time should be bigger than departure time");
            model.addAttribute("arrivaltime", "arrivaltime");
            return "homePage";
        }

        Page<Routes> routesList = routeService.routeSearch(PageRequest.of(page, 5), routeDto);

        if (routesList.isEmpty()){
            LOGGER.warn("No any matchers on route search");
        }

        model.addAttribute("routes",routesList);
        model.addAttribute("numbers", IntStream.range(0,routesList.getTotalPages()).toArray());

        LOGGER.debug("List of routes finded successfully");
        return "homePage";
    }

}
