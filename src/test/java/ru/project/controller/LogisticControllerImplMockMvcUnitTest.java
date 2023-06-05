package ru.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import ru.project.exception.InvalidRequestParametersException;
import ru.project.model.*;
import ru.project.repository.StationModelRepository;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LogisticControllerImplMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StationModelRepository repository;

    @Test
    public void givenWagonParkListAndParameters_whenAdd_thenStatus201andRailwayReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("HOUSEHOLD THINGS").build();
        Set<CargoModel> cargoList1 = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp = WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList1).cargoWeight(1021.0).weight(1121.0).exploitation(false).build();
        List<WagonPark> wagonsList = new ArrayList<>() {
            {
                add(wp);
            }
        };

        Railway rail = Railway.builder().nom(1).build();
        StationModel sm = StationModel.builder().name("Rastorguevo").build();
        sm.addRailway(rail);
        Mockito.when(repository.findStationModelByName(Mockito.any())).thenReturn(Optional.of(sm));
        rail.addWagonsToEnd(wagonsList);
        Mockito.when(repository.save(Mockito.any())).thenReturn(sm);

        mockMvc.perform(
                        post("/logistic")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .param("stationName", "Rastorguevo")
                                .param("railwayNom", "1")
                                .content(objectMapper.writeValueAsString(wagonsList))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(rail)));
    }

    @Test
    public void givenWagonParkListAndParameters_whenPostNotExistingStationModel_thenStatus404anExceptionThrown() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("HOUSEHOLD THINGS").build();
        Set<CargoModel> cargoList1 = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp = WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList1).cargoWeight(1021.0).weight(1121.0).exploitation(false).build();
        List<WagonPark> wagonsList = new ArrayList<>() {
            {
                add(wp);
            }
        };

        Mockito.when(repository.findStationModelByName(Mockito.any())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                        post("/logistic")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .param("stationName", "Rastorguevo")
                                .param("railwayNom", "1")
                                .content(objectMapper.writeValueAsString(wagonsList))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(java.lang.NumberFormatException.class));;

    }

    @Test
    public void givenWagonParkList_whenPostNotFoundParam_thenStatus404anExceptionThrown() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("HOUSEHOLD THINGS").build();
        Set<CargoModel> cargoList1 = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp = WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList1).cargoWeight(1021.0).weight(1121.0).exploitation(false).build();
        List<WagonPark> wagonsList = new ArrayList<>() {
            {
                add(wp);
            }
        };

        mockMvc.perform(
                        post("/logistic")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(wagonsList))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(InvalidRequestParametersException.class));;

    }

    @Test
    public void givenWagonParkListAndParameters_whenMoveWagons_thenStatus200andUpdatedRailwayReturns() throws Exception {

        WagonModel wm1 = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        WagonModel wm2 = WagonModel.builder().number("07712659").type(WagonModel.wagonType.HOPPER).tareWeight(111.0).tonnage(901.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("HOUSEHOLD THINGS").build();
        Set<CargoModel> cargoList1 = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp1 = WagonPark.builder().wagonModelNumber(wm1).cargoModelId(cargoList1).cargoWeight(1021.0).weight(1121.0).exploitation(false).build();
        WagonPark wp2= WagonPark.builder().wagonModelNumber(wm2).cargoModelId(cargoList1).cargoWeight(1000.0).weight(1111.0).exploitation(false).build();
        List<WagonPark> wagonsList1 = new ArrayList<>() {
            {
                add(wp1);
            }
        };
        List<WagonPark> wagonsList2 = new ArrayList<>() {
            {
                add(wp2);
            }
        };

        Railway rail = Railway.builder().id(11L).nom(1).build();
        StationModel sm = StationModel.builder().name("Rastorguevo").build();
        sm.addRailway(rail);
        rail.addWagonsToEnd(wagonsList1);
        Mockito.when(repository.findStationModelByName(Mockito.any())).thenReturn(Optional.of(sm));
        Mockito.when(repository.save(Mockito.any())).thenReturn(sm);

        mockMvc.perform(
                        put("/logistic")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .param("stationName", "Rastorguevo")
                                .param("railwayNom", "1")
                                .param("dir", "start")
                                .content(objectMapper.writeValueAsString(wagonsList2))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11L))
                .andExpect(jsonPath("$.nom").value(1))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.number").value("07712659"))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.type").value("HOPPER"))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.tareWeight").value(111.0))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.tonnage").value(901.0))
                .andExpect(jsonPath("$.wagons[0].cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.wagons[0].cargoModelId[0].name").value("HOUSEHOLD THINGS"))
                .andExpect(jsonPath("$.wagons[0].cargoWeight").value(1000.0))
                .andExpect(jsonPath("$.wagons[0].weight").value(1111.0))
                .andExpect(jsonPath("$.wagons[1].wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.wagons[1].wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.wagons[1].wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.wagons[1].wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.wagons[1].cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.wagons[1].cargoModelId[0].name").value("HOUSEHOLD THINGS"))
                .andExpect(jsonPath("$.wagons[1].cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.wagons[1].weight").value(1121.0));

    }

    @Test
    public void givenParameters_whenClearStationRailway_thenStatus200() throws Exception {

        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("HOUSEHOLD THINGS").build();
        Set<CargoModel> cargoList1 = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp = WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList1).cargoWeight(1021.0).weight(1121.0).exploitation(false).build();
        List<WagonPark> wagonsList = new ArrayList<>() {
            {
                add(wp);
            }
        };

        Railway rail = Railway.builder().nom(1).build();
        StationModel sm = StationModel.builder().name("Rastorguevo").build();
        sm.addRailway(rail);
        rail.addWagonsToEnd(wagonsList);
        Mockito.when(repository.findStationModelByName(Mockito.any())).thenReturn(Optional.of(sm));

        mockMvc.perform(
                        delete("/logistic")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .param("stationName", "Rastorguevo")
                                .param("railwayNom", "1")
                                .param("dir", "start")
                )
                .andExpect(status().isOk());


    }

}
