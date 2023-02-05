package me.kirenai.re.nourishment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.service.NourishmentService;
import me.kirenai.re.nourishment.util.NourishmentMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NourishmentController.class)
class NourishmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NourishmentService nourishmentService;

    private final StringBuilder URL = new StringBuilder("/api/v0/nourishments");

    @Test
    @DisplayName("Should return nourishment list")
    void getNourishmentList() throws Exception {
        List<NourishmentResponse> response = NourishmentMocks.getNourishmentResponseList();
        when(this.nourishmentService.findAll(any(Pageable.class)))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nourishmentId").value(response.get(0).getNourishmentId()))
                .andExpect(jsonPath("$[0].name").value(response.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(response.get(0).getDescription()))
                .andExpect(jsonPath("$[0].imageUrl").value(response.get(0).getImageUrl()))
                .andExpect(jsonPath("$[0].isAvailable").value(response.get(0).getIsAvailable()))
                .andExpect(jsonPath("$[0].unit").value(response.get(0).getUnit()))
                .andExpect(jsonPath("$[1].nourishmentId").value(response.get(1).getNourishmentId()))
                .andExpect(jsonPath("$[1].name").value(response.get(1).getName()))
                .andExpect(jsonPath("$[1].description").value(response.get(1).getDescription()))
                .andExpect(jsonPath("$[1].imageUrl").value(response.get(1).getImageUrl()))
                .andExpect(jsonPath("$[1].isAvailable").value(response.get(1).getIsAvailable()))
                .andExpect(jsonPath("$[1].percentage").value(response.get(1).getPercentage()))
                .andExpect(jsonPath("$[2].nourishmentId").value(response.get(2).getNourishmentId()))
                .andExpect(jsonPath("$[2].name").value(response.get(2).getName()))
                .andExpect(jsonPath("$[2].description").value(response.get(2).getDescription()))
                .andExpect(jsonPath("$[2].imageUrl").value(response.get(2).getImageUrl()))
                .andExpect(jsonPath("$[2].isAvailable").value(response.get(2).getIsAvailable()))
                .andExpect(jsonPath("$[2].unit").value(response.get(2).getUnit()));
    }

    @Test
    @DisplayName("Should return list of nourishments by user id")
    void getNourishmentsByUserIdTest() throws Exception {
        List<NourishmentResponse> response = NourishmentMocks.getNourishmentResponseList();
        when(this.nourishmentService.findAllByUserId(anyLong()))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.append("/user/1").toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nourishmentId").value(response.get(0).getNourishmentId()))
                .andExpect(jsonPath("$[0].name").value(response.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(response.get(0).getDescription()))
                .andExpect(jsonPath("$[0].imageUrl").value(response.get(0).getImageUrl()))
                .andExpect(jsonPath("$[0].isAvailable").value(response.get(0).getIsAvailable()))
                .andExpect(jsonPath("$[0].unit").value(response.get(0).getUnit()))
                .andExpect(jsonPath("$[1].nourishmentId").value(response.get(1).getNourishmentId()))
                .andExpect(jsonPath("$[1].name").value(response.get(1).getName()))
                .andExpect(jsonPath("$[1].description").value(response.get(1).getDescription()))
                .andExpect(jsonPath("$[1].imageUrl").value(response.get(1).getImageUrl()))
                .andExpect(jsonPath("$[1].isAvailable").value(response.get(1).getIsAvailable()))
                .andExpect(jsonPath("$[1].percentage").value(response.get(1).getPercentage()))
                .andExpect(jsonPath("$[2].nourishmentId").value(response.get(2).getNourishmentId()))
                .andExpect(jsonPath("$[2].name").value(response.get(2).getName()))
                .andExpect(jsonPath("$[2].description").value(response.get(2).getDescription()))
                .andExpect(jsonPath("$[2].imageUrl").value(response.get(2).getImageUrl()))
                .andExpect(jsonPath("$[2].isAvailable").value(response.get(2).getIsAvailable()))
                .andExpect(jsonPath("$[2].unit").value(response.get(2).getUnit()));
    }

    @Test
    @DisplayName("Should return list of available nourishments")
    void getAvailableNourishmentList() throws Exception {
        List<NourishmentResponse> response = NourishmentMocks.getNourishmentResponseList();
        when(this.nourishmentService.findAllByIsAvailable(anyBoolean()))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.append("/isAvailable/true").toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nourishmentId").value(response.get(0).getNourishmentId()))
                .andExpect(jsonPath("$[0].name").value(response.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(response.get(0).getDescription()))
                .andExpect(jsonPath("$[0].imageUrl").value(response.get(0).getImageUrl()))
                .andExpect(jsonPath("$[0].isAvailable").value(response.get(0).getIsAvailable()))
                .andExpect(jsonPath("$[0].unit").value(response.get(0).getUnit()))
                .andExpect(jsonPath("$[1].nourishmentId").value(response.get(1).getNourishmentId()))
                .andExpect(jsonPath("$[1].name").value(response.get(1).getName()))
                .andExpect(jsonPath("$[1].description").value(response.get(1).getDescription()))
                .andExpect(jsonPath("$[1].imageUrl").value(response.get(1).getImageUrl()))
                .andExpect(jsonPath("$[1].isAvailable").value(response.get(1).getIsAvailable()))
                .andExpect(jsonPath("$[1].percentage").value(response.get(1).getPercentage()))
                .andExpect(jsonPath("$[2].nourishmentId").value(response.get(2).getNourishmentId()))
                .andExpect(jsonPath("$[2].name").value(response.get(2).getName()))
                .andExpect(jsonPath("$[2].description").value(response.get(2).getDescription()))
                .andExpect(jsonPath("$[2].imageUrl").value(response.get(2).getImageUrl()))
                .andExpect(jsonPath("$[2].isAvailable").value(response.get(2).getIsAvailable()))
                .andExpect(jsonPath("$[2].unit").value(response.get(2).getUnit()));
    }

    @Test
    @DisplayName("Should create nourishment")
    void createNourishment() throws Exception {
        NourishmentResponse response = NourishmentMocks.getNourishmentResponse();
        when(this.nourishmentService.create(anyLong(), anyLong(), any(NourishmentRequest.class)))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.append("/user/1/category/1").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(NourishmentMocks.getNourishmentRequest()));

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.nourishmentId").value(response.getNourishmentId()))
                .andExpect(jsonPath("$.response.name").value(response.getName()))
                .andExpect(jsonPath("$.response.description").value(response.getDescription()))
                .andExpect(jsonPath("$.response.imageUrl").value(response.getImageUrl()))
                .andExpect(jsonPath("$.response.isAvailable").value(response.getIsAvailable()))
                .andExpect(jsonPath("$.response.unit").value(response.getUnit()));
    }

    @Test
    @DisplayName("Should update nourishment")
    void updateNourishment() throws Exception {
        NourishmentResponse response = NourishmentMocks
                .getNourishmentResponse();
        when(this.nourishmentService.update(anyLong(), any(NourishmentRequest.class)))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .put(this.URL.append("/1").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(NourishmentMocks.getNourishmentRequest()));

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(response))
                .andExpect(jsonPath("$.message").value("Successfully updated"));
    }


    @Test
    @DisplayName("Should delete nourishment")
    void deleteNourishment() throws Exception {
        doNothing().when(this.nourishmentService).delete(anyLong());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(this.URL.append("/1").toString());

        this.mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

}