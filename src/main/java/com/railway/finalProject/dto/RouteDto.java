package com.railway.finalProject.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Validation of the Route fiends
 *
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {
    private Long id;
    @NotEmpty(message = "You should set Departure Time")
    private String timeDeparture;
    @NotEmpty(message = "You should set Arrival Time")
    private String timeArrival;
    @NotEmpty(message = "You should set Number of Seats")
    @Min(value = 0,  message = "Value should be greater then then equal to 0")
    @Max(value = 100,  message = "Value should be less then then equal to 100")
    private String freePlaces;
    @NotEmpty(message = "You should set Ticket Price")
    @Min(value = 1,  message = "Value should be greater then then equal to 1")
    @Max(value = 1000,  message = "Value should be less then then equal to 1000")
    private String ticketPrice;
    @Min(value = 1, message = "You should set Station Departure")
    private int stationIdDeparture;
    @Min(value = 1, message = "You should set Station Arrival")
    private int stationIdArive;
}
