package com.railway.finalProject.service.impl;

import com.railway.finalProject.models.Ticket;
import com.railway.finalProject.repo.TicketRepository;
import com.railway.finalProject.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public boolean saveTicket(Ticket ticket) {
        LOGGER.info("Save new ticket to route and userlogin");
        ticketRepository.save(ticket);
        return true;
    }

    @Override
    public Page<Ticket> findAllPagebleTickets(PageRequest of, Long id) {
        LOGGER.info("Get all pageable tickets" );
        return ticketRepository.findAll(of, id);
    }

    @Override
    public Optional<Ticket> findUserTicketById(Long id, Long userId) {
        LOGGER.info("Find ticket by id = {}, of userId = {}", id, userId);
        if(ticketRepository.findUserTicketById(id, userId).isPresent()){
            LOGGER.info("Ticket with id = {} and userID = {} is exist", id, userId);
            return ticketRepository.findUserTicketById(id, userId);
        }
        LOGGER.warn("There is no ticket with id = {} and userID = {}", id, userId);
        return Optional.empty();
    }
}
