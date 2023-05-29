package com.railway.finalProject.controllers;


import com.railway.finalProject.utils.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketController {
    /**
     * Ticket Controller
     * Handles ticket get and post requests
     *
     */
    @Autowired
    private TicketUtil ticketUtil;

    @GetMapping("/ticket_buy/{idRoute}")
    public String stationUpdate(@PathVariable(value = "idRoute") Long id, Model model){
        return ticketUtil.routeTicket(id, model);
    }

    @PostMapping("/buy_ticket")
    public String saveNewTicket(@ModelAttribute("id") Long idRoute, Model model){
        return ticketUtil.saveTicketToUser(idRoute, model);
    }

    @GetMapping("/ticketlist")
    public String ticketList(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                             Model model){
        return ticketUtil.ticketList(page, model);
    }

    @GetMapping("/ticket/{id}")
    public String ticketPage(@PathVariable(value = "id") Long id, Model model){
        return ticketUtil.showTicket(id, model);
    }





}

