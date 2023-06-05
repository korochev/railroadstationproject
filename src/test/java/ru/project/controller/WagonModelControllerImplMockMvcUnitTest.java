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
import ru.project.model.WagonModel;
import ru.project.repository.WagonModelDAO;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WagonModelControllerImplMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WagonModelDAO repository;

    @Test
    public void givenWagonModel_whenAdd_thenStatus201andWagonModelReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(124.00).tonnage(1000).build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(wm);
        mockMvc.perform(
                        post("/wagon-model")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(wm))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(wm)));
    }

    @Test
    public void givenId_whenGetExistingWagonModel_thenStatus200andWagonModelReturned() throws Exception {
        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(124.00).tonnage(1000).build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(wm));

        mockMvc.perform(
                        get("/wagon-model/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("07722556"))
                .andExpect(jsonPath("$.type").value("GONDOLA"))
                .andExpect(jsonPath("$.tareWeight").value("124.0"))
                .andExpect(jsonPath("$.tonnage").value("1000.0"));
    }

    @Test
    public void givenId_whenGetNotExistingWagonModel_thenStatus404anExceptionThrown() throws Exception {

        Mockito.when(repository.findById(Mockito.any())).
                thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/wagon-model/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));;

    }

    @Test
    public void giveWagonModel_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {

        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(124.00).tonnage(1000).build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(wm);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(wm));

        mockMvc.perform(
                        put("/wagon-model/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN"))
                                .content(objectMapper.writeValueAsString(wm))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("07722556"))
                .andExpect(jsonPath("$.type").value("GONDOLA"))
                .andExpect(jsonPath("$.tareWeight").value("124.0"))
                .andExpect(jsonPath("$.tonnage").value("1000.0"));

    }

    @Test
    public void givenWagonModel_whenDeleteWagonModel_thenStatus200() throws Exception {

        WagonModel wm = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(124.00).tonnage(1000).build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(wm));

        mockMvc.perform(
                        delete("/wagon-model/07722556")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk());


    }

    @Test
    public void givenWagonModel_whenGetWagonModel_thenStatus200() throws Exception {
        WagonModel wm1 = WagonModel.builder().number("07722556").type(WagonModel.wagonType.GONDOLA).tareWeight(124.00).tonnage(1000).build();
        WagonModel wm2 = WagonModel.builder().number("07722700").type(WagonModel.wagonType.TANK).tareWeight(124.00).tonnage(1500).build();

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(wm1, wm2));

        mockMvc.perform(
                        get("/wagon-model")
                                .with(SecurityMockMvcRequestPostProcessors.user("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(wm1, wm2))));
        ;
    }
}
