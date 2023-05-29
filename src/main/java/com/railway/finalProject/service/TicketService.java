package com.railway.finalProject.service;

import com.railway.finalProject.models.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface TicketService {
    boolean saveTicket(Ticket ticket);

    Page<Ticket> findAllPagebleTickets(PageRequest of, Long id);

    Optional<Ticket> findUserTicketById(Long id, Long userId);
}
