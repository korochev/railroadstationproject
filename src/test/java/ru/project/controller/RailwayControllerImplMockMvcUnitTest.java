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
import ru.project.repository.RailwayRepository;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RailwayControllerImplMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RailwayRepository repository;

    @Test
    public void givenRailway_whenAdd_thenStatus201andRailwayReturned() throws Exception {
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
        Mockito.when(repository.save(Mockito.any())).thenReturn(railway);
        mockMvc.perform(
                        post("/railway")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(railway))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(railway)));
    }

    @Test
    public void givenId_whenGetExistingRailway_thenStatus200andRailwayReturned() throws Exception {
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

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(railway));

        mockMvc.perform(
                        get("/railway/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11L))
                .andExpect(jsonPath("$.nom").value(1))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.wagons[0].cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.wagons[0].cargoModelId[0].name").value("ВЕЩИ ДОМАШНИЕ"))
                .andExpect(jsonPath("$.wagons[0].cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.wagons[0].weight").value(1121.0));

    }

    @Test
    public void givenId_whenGetNotExistingRailway_thenStatus404anExceptionThrown() throws Exception {

        Mockito.when(repository.findById(Mockito.any())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/railway/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));;

    }

    @Test
    public void giveRailway_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

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

        Mockito.when(repository.save(Mockito.any())).thenReturn(railway);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(railway));

        mockMvc.perform(
                        put("/railway/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(railway))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11L))
                .andExpect(jsonPath("$.nom").value(1))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.wagons[0].wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.wagons[0].cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.wagons[0].cargoModelId[0].name").value("ВЕЩИ ДОМАШНИЕ"))
                .andExpect(jsonPath("$.wagons[0].cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.wagons[0].weight").value(1121.0));

    }

    @Test
    public void givenRailway_whenDeleteRailway_thenStatus200() throws Exception {

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

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(railway));

        mockMvc.perform(
                        delete("/railway/11")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk());


    }

    @Test
    public void givenRailway_whenGetRailway_thenStatus200() throws Exception {
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

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(railway1, railway2));

        mockMvc.perform(
                        get("/railway")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(railway1, railway2))));
        ;
    }
}
