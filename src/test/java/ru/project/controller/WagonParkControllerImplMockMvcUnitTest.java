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
import ru.project.model.CargoModel;
import ru.project.model.WagonModel;
import ru.project.model.WagonPark;
import ru.project.repository.WagonParkRepository;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WagonParkControllerImplMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WagonParkRepository repository;

    @Test
    public void givenCargoModel_whenAdd_thenStatus201andCargoModelReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(wp);
        mockMvc.perform(
                        post("/wagon-park")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(wp))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(wp)));
    }

    @Test
    public void givenId_whenGetExistingCargoModel_thenStatus200andCargoModelReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(wp));

        mockMvc.perform(
                        get("/wagon-park/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.cargoModelId[0].name").value("ВЕЩИ ДОМАШНИЕ"))
                .andExpect(jsonPath("$.cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.weight").value(1121.0));
    }

    @Test
    public void givenId_whenGetNotExistingCargoModel_thenStatus404anExceptionThrown() throws Exception {

        Mockito.when(repository.findById(Mockito.any())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/wagon-park/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));;

    }

    @Test
    public void giveCargoModel_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();

        Mockito.when(repository.save(Mockito.any())).thenReturn(wp);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(wp));

        mockMvc.perform(
                        put("/wagon-park/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(wp))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wagonModelNumber.number").value("07722556"))
                .andExpect(jsonPath("$.wagonModelNumber.type").value("GONDOLA"))
                .andExpect(jsonPath("$.wagonModelNumber.tareWeight").value(251.0))
                .andExpect(jsonPath("$.wagonModelNumber.tonnage").value(1021.0))
                .andExpect(jsonPath("$.cargoModelId[0].code").value("691005"))
                .andExpect(jsonPath("$.cargoModelId[0].name").value("ВЕЩИ ДОМАШНИЕ"))
                .andExpect(jsonPath("$.cargoWeight").value(1021.0))
                .andExpect(jsonPath("$.weight").value(1121.0));

    }

    @Test
    public void givenCargoModel_whenDeleteCargoModel_thenStatus200() throws Exception {

        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(wp));

        mockMvc.perform(
                        delete("/wagon-park/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk());


    }

    @Test
    public void givenCargoModel_whenGetCargoModel_thenStatus200() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(251.0).tonnage(1021.0).build();
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Set<CargoModel> cargoList = new HashSet<>() {
            {
                add(cm);
            }
        };
        WagonPark wp1= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1021.0).weight(1121.0).build();
        WagonPark wp2= WagonPark.builder().wagonModelNumber(wm).cargoModelId(cargoList).cargoWeight(1.0).weight(1001.0).build();


        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(wp1, wp2));

        mockMvc.perform(
                        get("/wagon-park")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(wp1, wp2))));
        ;
    }
}
