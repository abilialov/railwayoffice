package com.railway.finalProject.utils;

import com.railway.finalProject.dto.StationDto;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.repo.StationsRepository;
import com.railway.finalProject.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class StationUtil {

    private static final Logger LOGGER = LogManager.getLogger(StationUtil.class);

    @Autowired
    StationService stationService;

    public String getAllStations(Integer page, Model model){
        StationDto stationDto = new StationDto();
        model.addAttribute("station", stationDto);
        Page<Stations> stations = stationService.findAll(PageRequest.of(page,5));
        model.addAttribute("list_of_stations",stations);
        model.addAttribute("numbers", IntStream.range(0,stations.getTotalPages()).toArray());
        LOGGER.info("Upload pageable list of station in html");
        return "stationPage";
    }

    public String saveNewStation(StationDto stationDto, BindingResult result, Model model) {

        LOGGER.info("Save new station with name = {}", stationDto.getStationName());

        Stations existingStation = stationService.findStationByName(stationDto.getStationName());

        if(existingStation != null) {
            LOGGER.warn("Station with name = {} already exist", stationDto.getStationName());
            model.addAttribute("station", stationDto);
            return "redirect:/stations?alreadyexist";
        }

        if(result.hasErrors()){
            LOGGER.warn("Validation errors. Station should have a name.");
            model.addAttribute("station", stationDto);
            return "redirect:/stations?emptyerro";
        }

        stationService.saveStation(stationDto);
        LOGGER.debug("Station name = {} has been added successfully", stationDto.getStationName());
        return "redirect:/stations?success";
    }

    public String stationPage(Long id, Model model) {

        LOGGER.info("Opening station page with id = {}", id);

        if(!stationService.existsById(id)){
            LOGGER.warn("There is no any stations with id = {}", id);
            return "redirect:/";
        }
        Optional<Stations> stationById = stationService.findById(id);
        ArrayList<Stations> result = new ArrayList<>();
        stationById.ifPresent(result::add);
        StationDto stationDto = new StationDto();
        stationDto.setStationName(result.get(0).getStation_name());
        stationDto.setId(result.get(0).getId());
        model.addAttribute("station", stationDto);

        LOGGER.debug("Station with id ={} opened", id);
        return "stationUpdate";
    }

    public String removeStation(Long id, Model model) {
        Stations station = stationService.findById(id).orElseThrow();
        stationService.delete(station);
        LOGGER.debug("Station with id = {} removed successfully", id);
        return "redirect:/stations";
    }

    public String updateStationInfo(StationDto stationDto, BindingResult result, Model model) {

        LOGGER.info("Update station info, station id = {}", stationDto.getId());

        if(result.hasErrors()){
            LOGGER.warn("Station has validation errors, name should be not empty");
            return "redirect:/stations/" + stationDto.getId() + "?empty";
        }

        Stations existingStation = stationService.findStationByName(stationDto.getStationName());
        if(existingStation != null) {
            LOGGER.warn("There is already exist such station with name = {}", stationDto.getStationName());
            return "redirect:/stations/" + stationDto.getId() + "?alreadyexist";
        }

        stationService.updateStation(stationDto);
        LOGGER.debug("Station with id = {} had been successfully update, new name = {}",
                stationDto.getId(), stationDto.getStationName());
        return "redirect:/stations/" + stationDto.getId() + "?success";
    }
}
