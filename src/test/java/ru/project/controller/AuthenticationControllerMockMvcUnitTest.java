package ru.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.project.model.security.AuthenticationRequest;
import ru.project.model.security.AuthenticationResponse;
import ru.project.model.security.RegisterRequest;
import ru.project.model.security.user.User;
import ru.project.service.security.AuthenticationService;
import ru.project.service.security.jwt.JwtService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.project.model.security.user.Role.ADMIN;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationControllerMockMvcUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private AuthenticationResponse adminToken;

    @BeforeAll
    void registerUser() {
        adminToken = authenticationService.register(RegisterRequest.builder()
                .firstname("Admin")
                .lastname("Admin")
                .email("admin@mail.ru")
                .password("admin")
                .role(ADMIN)
                .build()
        );
    }

    @Test
    void getCargoModelCollection_noAuth_returnsIsForbidden() throws Exception {
        mockMvc.perform(get("/cargo-model")).andExpect(status().isForbidden());
    }

    @Test
    void getCargoModelCollection_withValidJwtToken_returnsOk() throws Exception {
        mockMvc.perform(get("/cargo-model")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken.getAccessToken())
        ).andExpect(status().isOk());
    }

    @Test
    void throwUserNotFoundException_withInvalidJwtToken() {
        String token = jwtService.generateToken(
                User.builder()
                        .firstname(RandomStringUtils.random(8, true, false))
                        .lastname(RandomStringUtils.random(10, true, false))
                        .email(RandomStringUtils.random(15, true, true))
                        .password(RandomStringUtils.random(10, true, true))
                        .role(ADMIN)
                        .build()
        );

        Assertions.assertThrows(UsernameNotFoundException.class, () ->
                mockMvc.perform(get("/cargo-model")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andExpect(status().isOk())
        );
    }

    @Test
    void successAuthentication_WithExistingUser() throws Exception {
        mockMvc.perform(post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                        .email("admin@mail.ru")
                        .password("admin")
                        .build()))
        ).andExpect(status().isOk());
    }

    @Test
    void failAuthentication_WithExistingUser_WithCorrectEmail_WithWrongPassword() throws Exception {
        mockMvc.perform(post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                        .email("admin@mail.ru")
                        .password("user")
                        .build()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void failAuthentication_WithExistingUser_WithWrongEmail_WithCorrectPassword() throws Exception {
        mockMvc.perform(post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                        .email("user@mail.ru")
                        .password("admin")
                        .build()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void successRegistration_ForNewUser() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RegisterRequest.builder()
                        .firstname(RandomStringUtils.random(8, true, false))
                        .lastname(RandomStringUtils.random(10, true, false))
                        .email(RandomStringUtils.random(15, true, true))
                        .password(RandomStringUtils.random(10, true, true))
                        .role(ADMIN)
                        .build()))
        ).andExpect(status().isOk());
    }
}
