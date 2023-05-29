package com.railway.finalProject.utils;

import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.models.Routes;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.models.Ticket;
import com.railway.finalProject.models.Users;
import com.railway.finalProject.service.RouteService;
import com.railway.finalProject.service.StationService;
import com.railway.finalProject.service.TicketService;
import com.railway.finalProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TicketUtil {

    private static final Logger LOGGER = LogManager.getLogger(TicketUtil.class);

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    public String routeTicket(Long id, Model model) {

        RouteDto routeDto = new RouteDto();

        Optional<Routes> routeById = routeService.findRouteById(id);
        ArrayList<Routes> result = new ArrayList<>();
        routeById.ifPresent(result::add);

        /** find name of station departure */
        Optional<Stations> stationDepById = stationService.findById((long) result.get(0).getStationDepartureInRoutes().getId());
        ArrayList<Stations> resultStationDep = new ArrayList<>();
        stationDepById.ifPresent(resultStationDep::add);
        model.addAttribute("stationDepName", resultStationDep.get(0).getStation_name().toUpperCase());

        /** find name of station arrival */
        Optional<Stations> stationArrById = stationService.findById((long) result.get(0).getStationArrivalInRoutes().getId());
        ArrayList<Stations> resultStationArr = new ArrayList<>();
        stationArrById.ifPresent(resultStationArr::add);
        model.addAttribute("stationArrName", resultStationArr.get(0).getStation_name().toUpperCase());


        routeDto.setStationIdDeparture(Math.toIntExact(result.get(0).getStationDepartureInRoutes().getId()));
        routeDto.setStationIdArive(Math.toIntExact(result.get(0).getStationArrivalInRoutes().getId()));
        routeDto.setTimeDeparture(result.get(0).getTime_departure());
        routeDto.setTimeArrival(result.get(0).getTime_arrival());
        routeDto.setTicketPrice(String.valueOf(result.get(0).getTicket_price()));
        routeDto.setFreePlaces(String.valueOf(result.get(0).getFree_places()));
        routeDto.setId(result.get(0).getId());

        if (result.get(0).getFree_places() < 1){
            LOGGER.warn("Ticket limit over on route id = {}", routeDto.getId());
            model.addAttribute("ticketLimit", "Ticket limit over");
        }

        model.addAttribute("route", routeDto);
        LOGGER.info("Load page to buy a ticket to route id = {}", routeDto.getId());
        return "routeTicketPage";
    }

    public String saveTicketToUser(Long id, Model model) {

        LOGGER.info("Save ticket from route id = {}", id);

        if(!routeService.existsRouteById(id)){
            LOGGER.error("Route with id = {} no exist", id);
            return "redirect:/ticket_buy/ "+id + "?notfound";
        }

        /** find route by id */
        Optional<Routes> routeById = routeService.findRouteById(id);
        ArrayList<Routes> result = new ArrayList<>();
        routeById.ifPresent(result::add);

        if(result.get(0).getFree_places()<1){
            LOGGER.warn("No more free places on route id = {}", id);
            return "redirect:/ticket_buy/ "+id + "?freeplaces";
        }

        Users client = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        Ticket ticket = new Ticket();
        ticket.setTicket_price(Long.valueOf(result.get(0).getTicket_price()));
        ticket.setRouteInTicket(new Routes(result.get(0).getId()));
        ticket.setUserInTicket(new Users(client.getId()));

        RouteDto routeDto = new RouteDto();
        routeDto.setId(result.get(0).getId());
        routeDto.setTimeDeparture(result.get(0).getTime_departure());
        routeDto.setTimeArrival(result.get(0).getTime_arrival());
        routeDto.setFreePlaces(String.valueOf(result.get(0).getFree_places()-1));
        routeDto.setTicketPrice(String.valueOf(result.get(0).getTicket_price()));
        routeDto.setStationIdDeparture(Math.toIntExact(result.get(0).getStationDepartureInRoutes().getId()));
        routeDto.setStationIdArive(Math.toIntExact(result.get(0).getStationArrivalInRoutes().getId()));

        ticketService.saveTicket(ticket);
        LOGGER.debug("Ticket has been bought to route id = {}", id);
        routeService.updateRoute(routeDto);
        LOGGER.debug("Free places on route id ={} had been reduce on 1 ticket", id);

        return "redirect:/?ticketsuccess";
    }

    public String ticketList(Integer page, Model model) {

        Users currentAuthUser = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        Long userId = (currentAuthUser.getId());

        Page<Ticket> ticketsList = ticketService.findAllPagebleTickets(PageRequest.of(page, 5), userId);

        model.addAttribute("numbers", IntStream.range(0,ticketsList.getTotalPages()).toArray());
        model.addAttribute("ticketlist", ticketsList);

        LOGGER.debug("Load ticketlist for userId = {}", userId);
        return "ticketListPage";

    }

    public String showTicket(Long id, Model model) {

        LOGGER.info("Show ticket info for ticket id ={}", id);

        Users client = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Long userID = client.getId();


        Optional<Ticket> ticketById = ticketService.findUserTicketById(id, userID);
        ArrayList<Ticket> result = new ArrayList<>();
        ticketById.ifPresent(result::add);

        if(result.isEmpty() ){
            LOGGER.error("NO SUCH TICKET id = {} TO CURRENT USER userid = {}", id, userID);
            return "error/error";
        }

        model.addAttribute("ticket", result.get(0));

        LOGGER.info("Ticket info ticketid = {} load successfully", id);
        return "ticket";

    }
}


























