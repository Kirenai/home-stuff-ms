package me.kirenai.re.consumption.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.service.ConsumptionService;
import me.kirenai.re.consumption.util.ConsumptionMocks;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConsumptionController.class)
class ConsumptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConsumptionService consumptionService;

    private final StringBuilder URL = new StringBuilder("/api/consumptions");

    @Test
    @DisplayName("Should return all consumptions")
    void getAllConsumptionsTest() throws Exception {
        List<ConsumptionResponse> response = ConsumptionMocks.getConsumptionResponseList();
        when(this.consumptionService.findAll(any(Pageable.class)))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.toString())
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].unit").value(response.get(0).getUnit()))
                .andExpect(jsonPath("$[1].percentage").value(response.get(1).getPercentage()))
                .andExpect(jsonPath("$[2].unit").value(response.get(2).getUnit()));
    }

    @Test
    @DisplayName("Should return consumption by id")
    void getConsumptionById() throws Exception {
        ConsumptionResponse response = ConsumptionMocks.getConsumptionResponse();
        when(this.consumptionService.findOne(anyLong()))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.append("/1").toString())
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unit").value(response.getUnit()));
    }

    @Test
    @DisplayName("Should create consumption")
    void createConsumption() throws Exception {
        ConsumptionResponse response = ConsumptionMocks.getConsumptionResponse();
        when(this.consumptionService.create(anyLong(), anyLong(), any(ConsumptionRequest.class)))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.append("?userId=1&nourishmentId=1").toString())
                .content(this.objectMapper.writeValueAsString(ConsumptionMocks.getConsumptionRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.consumptionId").value(response.getConsumptionId()))
                .andExpect(jsonPath("$.unit").value(response.getUnit()));
    }

}