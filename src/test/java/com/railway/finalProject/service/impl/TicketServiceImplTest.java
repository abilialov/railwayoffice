package com.railway.finalProject.service.impl;

import com.railway.finalProject.models.Routes;
import com.railway.finalProject.models.Ticket;
import com.railway.finalProject.models.Users;
import com.railway.finalProject.repo.TicketRepository;
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
public class TicketServiceImplTest {

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketServiceImpl ticketService;

    @Test
    public void saveTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicket_price(55L);

        boolean isTicketCreated = ticketService.saveTicket(ticket);
        Assert.assertTrue(isTicketCreated);

    }

    @Test
    public void findAllPagebleTickets() {
        Page<Ticket> ticketPageList = new PageImpl<>(getTickets());
        PageRequest of = PageRequest.of(0, 2);
        Long userID = 1L;
        Mockito.when(ticketRepository.findAll(of, userID)).thenReturn(ticketPageList);

        Page<Ticket> resultTicket = ticketService.findAllPagebleTickets(of,userID);

        Assert.assertNotNull(resultTicket);
        Assert.assertEquals(resultTicket.getTotalPages(), ticketPageList.getTotalPages());
        Assert.assertEquals(resultTicket.getTotalElements(), ticketPageList.getTotalElements());
        Assert.assertEquals(resultTicket, ticketPageList);
    }

    private List<Ticket> getTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        Users user = new Users();
        user.setId(1L);

        Routes route = new Routes();
        route.setId(1L);

        ticket1.setID(1L);
        ticket1.setTicket_price(55L);
        ticket1.setUserInTicket(user);
        ticket1.setRouteInTicket(route);

        ticket2.setID(2L);
        ticket2.setTicket_price(77L);
        ticket2.setUserInTicket(user);
        ticket2.setRouteInTicket(route);

        return List.of(ticket1, ticket2);
    }

    @Test
    public void findUserTicketById() {
        Long id = 1L;
        Long userId = 1L;
        Ticket ticketMock =  getTickets().get(0);
        Mockito.when(ticketRepository.findUserTicketById(id, userId)).thenReturn(Optional.ofNullable(ticketMock));

        Optional<Ticket> resultTicket = ticketService.findUserTicketById(id, userId);

        Assert.assertTrue(resultTicket.isPresent());
        Assert.assertEquals(resultTicket.get().getTicket_price(), ticketMock.getTicket_price());
        Assert.assertEquals(resultTicket.get(), ticketMock);

    }
}