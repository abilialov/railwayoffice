package com.railway.finalProject.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Validation of the Station fiends
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

    private Long id;
    @NotEmpty(message = "Station name should not be empty")
    private String stationName;

}
