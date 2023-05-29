package com.railway.finalProject.controllers;

import com.railway.finalProject.dto.RouteDto;
import com.railway.finalProject.utils.RouteUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoutesController {

    /**
     * Routes Controller
     * Handles get and post requests and all info that comes from the forms
     */
    @Autowired
    private RouteUtil routeUtil;

    @GetMapping("/routes")
    public String routes (Model model){
        return routeUtil.createNewRouteForm(model);
    }

    @GetMapping("/route_update/{idRoute}")
    public String stationUpdate(@PathVariable(value = "idRoute") Long id, Model model){
        return routeUtil.updateRoute(id, model);
    }

    @PostMapping("/routecreate")
    public String routeCreate(@Valid @ModelAttribute("route") RouteDto routeDto,
                              BindingResult result, Model model){
        return routeUtil.createRoute(routeDto, result, model);
    }

    @PostMapping("/routeupdate")
    public String routeUpdate(@Valid @ModelAttribute("route") RouteDto routeDto,
                              BindingResult result, Model model){
        return routeUtil.updateRouteInfo(routeDto, result, model);
    }

    @GetMapping("/routesearch")
    public String routeSearch (@RequestParam (value = "page", required = false, defaultValue = "0") Integer page,
                               @ModelAttribute("route") RouteDto routeDto,
                               BindingResult result, Model model){
        return routeUtil.findRoute(page, routeDto, result, model);
    }

}
