package com.jonathan.github.addressapi.rest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jonathan.github.addressapi.rest.DTO.AddressDTO;
import com.jonathan.github.addressapi.rest.DTO.MapsApiResponseDTO;
import com.jonathan.github.addressapi.service.MapsService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressResources.class)
class AddressResourcesTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MapsService service;


    @Test
    public void shouldReturnBadRequestWithEmptyBody() throws Exception {
         mvc.perform(MockMvcRequestBuilders.post("/api/address")
                         .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").exists());

    }

    @Test
    public void shouldReturnOkAndContainsBody() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/address")
                        .content("["+ asJsonString(new AddressDTO
                                ("Estr. de Paciência", 655, 23066271,
                                        "Rio de Janeiro", "Campo Grande")) +"]")
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}