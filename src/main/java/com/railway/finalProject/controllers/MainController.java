package com.railway.finalProject.controllers;

import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.repo.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private StationsRepository stationsRepository;

    /**
     *
     * @param model
     * @return html template with main pae
     */
    @GetMapping("/")
    public String home ( Model model) {

        RouteDto routeDto = new RouteDto();
        model.addAttribute("route", routeDto);
        model.addAttribute("title", "Main page");
        Iterable<Stations> stations = stationsRepository.findAll();
        model.addAttribute("list_of_stations",stations);
        return "homePage";
    }

}