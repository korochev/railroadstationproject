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
import ru.project.exception.EntityNotFoundException;
import ru.project.model.*;
import ru.project.repository.StationModelRepository;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StationModelControllerImplMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StationModelRepository repository;

    @Test
    public void givenStationModel_whenAdd_thenStatus201andStationModelReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Railway railway = Railway.builder().id(11L).nom(1).build();
        List<WagonPark> wagonsList = new LinkedList<>() {
            {
                add(wp);
            }
        };
        railway.addWagonsToEnd(wagonsList);
        StationModel sm = StationModel.builder().id(11L).name("Расторгуево").build();
        sm.addRailway(railway);

        Mockito.when(repository.save(Mockito.any())).thenReturn(sm);
        mockMvc.perform(
                        post("/station-model")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(sm))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(sm)));
    }

    @Test
    public void givenId_whenGetExistingStationModel_thenStatus200andStationModelReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Railway railway = Railway.builder().id(11L).nom(1).build();
        List<WagonPark> wagonsList = new LinkedList<>() {
            {
                add(wp);
            }
        };
        railway.addWagonsToEnd(wagonsList);
        StationModel sm = StationModel.builder().id(11L).name("Расторгуево").build();
        sm.addRailway(railway);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(sm));

        mockMvc.perform(
                        get("/station-model/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11L))
                .andExpect(jsonPath("$.name").value("Расторгуево"))
                .andExpect(jsonPath("$.railways[0].id").value(11L))
                .andExpect(jsonPath("$.railways[0].nom").value(1))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.railways[0].wagons[0].cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.railways[0].wagons[0].cargoModelId[0].name").value("ВЕЩИ ДОМАШНИЕ"))
                .andExpect(jsonPath("$.railways[0].wagons[0].cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.railways[0].wagons[0].weight").value(1121.0));
    }

    @Test
    public void givenId_whenGetNotExistingStationModel_thenStatus404anExceptionThrown() throws Exception {

        Mockito.when(repository.findById(Mockito.any())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/station-model/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));;

    }

    @Test
    public void giveStationModel_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Railway railway = Railway.builder().id(11L).nom(1).build();
        List<WagonPark> wagonsList = new LinkedList<>() {
            {
                add(wp);
            }
        };
        railway.addWagonsToEnd(wagonsList);
        StationModel sm = StationModel.builder().id(11L).name("Расторгуево").build();
        sm.addRailway(railway);

        Mockito.when(repository.save(Mockito.any())).thenReturn(sm);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(sm));

        mockMvc.perform(
                        put("/station-model/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(sm))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11L))
                .andExpect(jsonPath("$.name").value("Расторгуево"))
                .andExpect(jsonPath("$.railways[0].id").value(11L))
                .andExpect(jsonPath("$.railways[0].nom").value(1))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.railways[0].wagons[0].wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.railways[0].wagons[0].cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.railways[0].wagons[0].cargoModelId[0].name").value("ВЕЩИ ДОМАШНИЕ"))
                .andExpect(jsonPath("$.railways[0].wagons[0].cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.railways[0].wagons[0].weight").value(1121.0));

    }

    @Test
    public void givenStationModel_whenDeleteStationModel_thenStatus200() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Railway railway = Railway.builder().id(11L).nom(1).build();
        List<WagonPark> wagonsList = new LinkedList<>() {
            {
                add(wp);
            }
        };
        railway.addWagonsToEnd(wagonsList);
        StationModel sm = StationModel.builder().id(11L).name("Расторгуево").build();
        sm.addRailway(railway);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(sm));

        mockMvc.perform(
                        delete("/station-model/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk());


    }

    @Test
    public void givenStationModel_whenGetStationModel_thenStatus200() throws Exception {
        WagonModel wm1 = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        WagonModel wm2 = WagonModel.builder().number("07747960").type(WagonModel.wagonType.TANK).tareWeight(555.0).tonnage(1600.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp1= WagonPark.builder().wagonModelNumber(wm1).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        WagonPark wp2= WagonPark.builder().wagonModelNumber(wm2).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Railway railway1 = Railway.builder().id(11L).nom(1).build();
        Railway railway2 = Railway.builder().id(12L).nom(1).build();
        List<WagonPark> wagonsList1 = new LinkedList<>() {
            {
                add(wp1);
            }
        };
        List<WagonPark> wagonsList2 = new LinkedList<>() {
            {
                add(wp2);
            }
        };

        railway1.addWagonsToEnd(wagonsList1);
        railway2.addWagonsToEnd(wagonsList2);

        StationModel sm1 = StationModel.builder().id(11L).name("Расторгуево").build();
        StationModel sm2 = StationModel.builder().id(13L).name("Взлетная").build();

        sm1.addRailway(railway1);
        sm2.addRailway(railway2);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(sm1, sm2));

        mockMvc.perform(
                        get("/station-model")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(sm1, sm2))));
        ;
    }
}
