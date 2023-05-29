package com.railway.finalProject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String station_name;

    @OneToMany(mappedBy = "stationDepartureInRoutes", cascade = CascadeType.ALL)
    private List<Routes> routesListDeparture;

    @OneToMany(mappedBy = "stationArrivalInRoutes", cascade = CascadeType.ALL)
    private List<Routes> routesListArrival;

    public Stations(Long id) {
        this.id = id;
    }

    public Stations(String station_name) {
        this.station_name = station_name;
    }

    public Stations(Long id, String stationName) {
        this.id = id;
        this.station_name = stationName;
    }

    @Override
    public String toString() {
        return this.station_name;
    }
}
