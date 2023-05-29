package com.railway.finalProject.service.impl;

import com.railway.finalProject.dto.StationDto;
import com.railway.finalProject.models.Stations;
import com.railway.finalProject.repo.StationsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StationServiceImplTest {

    @MockBean
    private StationsRepository stationsRepository;

    @Autowired
    private StationServiceImpl stationService;

    private List<Stations> getStations(){
        Stations station1 = new Stations();
        Stations station2 = new Stations();

        station1.setId(1L);
        station1.setStation_name("Station 1");

        station2.setId(2L);
        station2.setStation_name("Station 2");

        return List.of(station1, station2);
    }

    @Test
    public void saveStation() {
        StationDto stationDto = new StationDto();
        stationDto.setStationName("stationdto");

        boolean isStationCreated = stationService.saveStation(stationDto);
        Assert.assertTrue(isStationCreated);
    }

    @Test
    public void testFindStationByName() {

        Stations stationsMock = getStations().get(0);
        String stationName = "Station 1";

        Mockito.when(stationsRepository.findStationByName(stationName)).thenReturn(stationsMock);
        Stations resultStation = stationService.findStationByName(stationName);

        Assert.assertNotNull(resultStation);
        Assert.assertEquals(resultStation.getStation_name(), stationsMock.getStation_name());
        Assert.assertEquals(resultStation, stationsMock);

    }

    @Test
    public void existsById() {
        Long id = 1L;
        Mockito.when(stationsRepository.existsById(id)).thenReturn(true);

        boolean isStationExist = stationService.existsById(id);

        Assert.assertTrue(isStationExist);
    }

    @Test
    public void findById() {
        Long id = 1L;
        Stations stationMock = getStations().get(0);
        Mockito.when(stationsRepository.findById(id)).thenReturn(Optional.ofNullable(stationMock));

        Optional<Stations> resultStation = stationService.findById(id);

        Assert.assertTrue(resultStation.isPresent());
        Assert.assertEquals(resultStation.get().getStation_name(), stationMock.getStation_name());
        Assert.assertEquals(resultStation.get(), stationMock);
    }

    @Test
    public void delete() {

    }

    @Test
    public void updateStation() {
        StationDto stationDto = new StationDto();
        stationDto.setStationName("stationdto");
        stationDto.setId(1l);

        boolean isStationCreated = stationService.updateStation(stationDto);
        Assert.assertTrue(isStationCreated);
    }

    @Test
    public void findFullStationList() {

        List<Stations> stationsFromMock = getStations();
        Mockito.when(stationsRepository.findAll()).thenReturn(stationsFromMock);

        Iterable<Stations> resultStationList = stationService.findFullStationList();

        Assert.assertNotNull(resultStationList);
        Assert.assertEquals(resultStationList, stationsFromMock);

    }

    @Test
    public void findAll() {
        Page<Stations> stationsFromMock = new PageImpl<>(getStations());
        PageRequest of = PageRequest.of(0, 2);
        Mockito.when(stationsRepository.findAll(of)).thenReturn(stationsFromMock);

        Page<Stations> resultStations = stationService.findAll(of);

        Assert.assertNotNull(resultStations);
        Assert.assertEquals(resultStations.getTotalPages(), stationsFromMock.getTotalPages());
        Assert.assertEquals(resultStations.getTotalElements(), stationsFromMock.getTotalElements());
        Assert.assertEquals(resultStations, stationsFromMock);

    }
}