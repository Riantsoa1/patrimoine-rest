package com.hei.patrimoinerest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hei.patrimoinerest.controller.PatrimoineController;

import com.hei.patrimoinerest.model.Patrimoine;
import com.hei.patrimoinerest.service.PatrimoineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@WebMvcTest(PatrimoineController.class)
public class PatrimoineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatrimoineService patrimoineService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
    }
    @Test
    public void testGetPatrimoine() throws Exception {
        String id = "1";
        Patrimoine patrimoine = new Patrimoine(id, "Jean Dupont", LocalDateTime.now());

        when(patrimoineService.getPatrimoine(id)).thenReturn(Optional.of(patrimoine));

        mockMvc.perform(get("/patrimoines/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.possesseur").value("Jean Dupont"));

        }

    @Test
    public void testCreateOrUpdatePatrimoine() throws Exception {
        String id = "1";
        String possesseur = "Jean Dupont";
        Patrimoine patrimoine = new Patrimoine(id, possesseur, LocalDateTime.now());

        // Simuler le comportement du service
        when(patrimoineService.savePatrimoine(id, possesseur)).thenReturn(patrimoine);

        mockMvc.perform(put("/patrimoines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(possesseur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.possesseur").value(possesseur));
    }
    };
