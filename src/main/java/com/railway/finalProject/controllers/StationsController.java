package com.railway.finalProject.controllers;


import com.railway.finalProject.dto.StationDto;
import com.railway.finalProject.utils.StationUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StationsController implements ErrorController {

    /**
     * Station Controller
     * Handles get and post requests and all info that comes from the forms
     */
    @Autowired
    private StationUtil stationUtil;


    @GetMapping ("/stations")
    public String station(@RequestParam (value = "page", required = false, defaultValue = "0") Integer page, Model model){
        return stationUtil.getAllStations(page, model);
    }

    @PostMapping("/stations/save")
    public String stationPostAdd(@Valid @ModelAttribute("station") StationDto stationDto,
                                 BindingResult result, Model model){
        return stationUtil.saveNewStation(stationDto, result, model);
    }
    @GetMapping("/stations/{idStation}")
    public String stationUpdate(@PathVariable(value = "idStation") Long id, Model model){
       return stationUtil.stationPage(id, model);
    }
    @PostMapping("/stations/{idStation}/delete")
    public String stationPostRemove(@PathVariable(value = "idStation") Long id, Model model){
        return stationUtil.removeStation(id, model);
    }
    @PostMapping("/stations_update")
    public String stationPostUpdate(@Valid @ModelAttribute("station") StationDto stationDto,
                                    BindingResult result, Model model){
        return stationUtil.updateStationInfo(stationDto, result, model);
    }


}
