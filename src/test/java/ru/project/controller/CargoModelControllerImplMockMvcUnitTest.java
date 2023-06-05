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
import ru.project.repository.CargoModelDAO;


import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CargoModelControllerImplMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CargoModelDAO repository;


    @Test
    public void givenCargoModel_whenAdd_thenStatus201andCargoModelReturned() throws Exception {
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(cm);
        mockMvc.perform(
                        post("/cargo-model")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(cm))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(cm)));
    }



    @Test
    public void givenId_whenGetExistingCargoModel_thenStatus200andCargoModelReturned() throws Exception {
        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cm));

        mockMvc.perform(
                        get("/cargo-model/691005")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("691005"))
                .andExpect(jsonPath("$.name").value("ВЕЩИ ДОМАШНИЕ"));
    }

    @Test
    public void givenId_whenGetNotExistingCargoModel_thenStatus404anExceptionThrown() throws Exception {

        Mockito.when(repository.findById(Mockito.any())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/cargo-model/691005")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));;

    }

    @Test
    public void giveCargoModel_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(cm);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cm));

        mockMvc.perform(
                        put("/cargo-model/691005")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(cm))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("691005"))
                .andExpect(jsonPath("$.name").value("ВЕЩИ ДОМАШНИЕ"));

    }

    @Test
    public void givenCargoModel_whenDeleteCargoModel_thenStatus200() throws Exception {

        CargoModel cm = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cm));

        mockMvc.perform(
                        delete("/cargo-model/691005")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk());


    }

    @Test
    public void givenCargoModel_whenGetCargoModel_thenStatus200() throws Exception {
        CargoModel cm1 = CargoModel.builder().code("691005").name("ВЕЩИ ДОМАШНИЕ").build();
        CargoModel cm2 = CargoModel.builder().code("011005").name("ПШЕНИЦА").build();

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(cm1, cm2));

        mockMvc.perform(
                        get("/cargo-model")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(cm1, cm2))));
        ;
    }
}
