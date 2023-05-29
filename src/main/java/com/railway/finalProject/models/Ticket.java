package com.railway.finalProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private Long ticket_price;

    @ManyToOne
    @JoinColumn(name = "routes_ID", referencedColumnName = "ID", insertable = true, updatable = true)
    private Routes routeInTicket;

    @ManyToOne
    @JoinColumn(name = "user_ID", referencedColumnName = "ID", insertable = true, updatable = true)
    private Users userInTicket;

}






















