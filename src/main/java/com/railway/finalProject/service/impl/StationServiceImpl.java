package com.railway.finalProject.service.impl;

import com.railway.finalProject.dto.StationDto;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.repo.StationsRepository;
import com.railway.finalProject.service.StationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class StationServiceImpl implements StationService {

    private static final Logger LOGGER = LogManager.getLogger(StationServiceImpl.class);

    private StationsRepository stationsRepository;

    public StationServiceImpl(StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }

    @Override
    public boolean saveStation(StationDto stationDto) {
        LOGGER.info("Save new station with name = {}", stationDto.getStationName());
        Stations station = new Stations();
        station.setStation_name(stationDto.getStationName());
        stationsRepository.save(station);
        return true;
    }

    @Override
    public Stations findStationByName(String stationName) {
        LOGGER.info("Find station by name = {}",  stationName);
        return stationsRepository.findStationByName(stationName);
    }

    @Override
    public boolean existsById(Long id) {
        LOGGER.info("Check station by id = {}", id);
        return stationsRepository.existsById(id);
    }

    @Override
    public Optional<Stations> findById(Long id) {
        LOGGER.info("Find station by id = {}", id);
        if(stationsRepository.findById(id).isPresent()){
            LOGGER.debug("Station with id = {} is exist", id);
            return stationsRepository.findById(id);
        }
        LOGGER.warn("Station with id = {} not exist", id);
        return Optional.empty();
    }

    @Override
    public void delete(Stations station) {
        LOGGER.info("Delete station, name = {}, id ={}", station.getStation_name(), station.getId());
        stationsRepository.delete(station);
    }

    @Override
    public boolean updateStation(StationDto stationDto) {
        LOGGER.info("Update station name = {}, id = {}", stationDto.getStationName(), stationDto.getId());
        Stations station = new Stations(stationDto.getId(), stationDto.getStationName());
        stationsRepository.save(station);
        return true;
    }

    @Override
    public Iterable<Stations> findFullStationList() {
        LOGGER.info("Get list of all stations");
        return stationsRepository.findAll();
    }

    @Override
    public Page<Stations> findAll(Pageable pageable) {
        LOGGER.info("Get pageable list of all stations");
        return stationsRepository.findAll(pageable);
    }
}
