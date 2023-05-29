package com.railway.finalProject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * Displaying database table entities
 *
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String time_departure;
    private String time_arrival;
    private int free_places;
    private int ticket_price;


    @OneToMany(mappedBy = "routeInTicket")
    private List<Ticket> ticket;

    @ManyToOne
    @JoinColumn(name = "station_id_departure", referencedColumnName = "ID", insertable = true, updatable = true)
    private Stations stationDepartureInRoutes;

    @ManyToOne
    @JoinColumn(name = "station_id_arive", referencedColumnName = "ID", insertable = true, updatable = true)
    private Stations stationArrivalInRoutes;

    public Routes(Long id){
        this.id = id;
    }

}
