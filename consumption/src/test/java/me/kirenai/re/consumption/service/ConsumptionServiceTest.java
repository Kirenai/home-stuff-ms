package me.kirenai.re.consumption.service;

import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.consumption.dto.UserResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import me.kirenai.re.consumption.util.ConsumptionClient;
import me.kirenai.re.consumption.util.ConsumptionMocks;
import me.kirenai.re.consumption.util.NourishmentMocks;
import me.kirenai.re.consumption.util.UserMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumptionServiceTest {

    @InjectMocks
    private ConsumptionService consumptionService;
    @Mock
    private ConsumptionRepository consumptionRepository;
    @Mock
    private ConsumptionMapper mapper;
    @Mock
    private ConsumptionClient client;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should find a consumption list when finding all")
    void shouldFindConsumptionListWhenFindingAll() {
        Pageable pageableMock = mock(Pageable.class);
        List<ConsumptionResponse> consumptionResponse = ConsumptionMocks.getConsumptionResponseList();

        when(this.consumptionRepository.findAll(any(Pageable.class))).thenReturn(ConsumptionMocks.getConsumptionPage());
        when(this.mapper.mapOutConsumptionToConsumptionResponse(any(Consumption.class)))
                .thenReturn(consumptionResponse.get(0), consumptionResponse.get(1), consumptionResponse.get(2));

        List<ConsumptionResponse> response = consumptionService.findAll(pageableMock);

        assertEquals(consumptionResponse.size(), response.size());
        assertEquals(consumptionResponse, response);

        verify(this.consumptionRepository, times(1)).findAll(any(Pageable.class));
        verify(this.mapper, times(3)).mapOutConsumptionToConsumptionResponse(any());
    }

    @Test
    @DisplayName("Should find a consumption when finding one")
    void shouldFindConsumptionWhenFindingOne() {
        ConsumptionResponse consumptionResponse = ConsumptionMocks.getConsumptionResponse();

        when(this.consumptionRepository.findById(anyLong())).thenReturn(Optional.of(ConsumptionMocks.getConsumption()));
        when(this.mapper.mapOutConsumptionToConsumptionResponse(any(Consumption.class))).thenReturn(consumptionResponse);

        ConsumptionResponse response = consumptionService.findOne(1L);

        assertEquals(consumptionResponse, response);

        verify(this.consumptionRepository, times(1)).findById(anyLong());
        verify(this.mapper, times(1)).mapOutConsumptionToConsumptionResponse(any());
    }

    @Test
    @DisplayName("Should create a consumption")
    void shouldCreateConsumptionWhenUserHasAuthorizationWhenCreating() {
        ConsumptionResponse consumptionResponse = ConsumptionMocks.getConsumptionResponse();

        when(this.mapper.mapInConsumptionRequestToConsumption(any()))
                .thenReturn(ConsumptionMocks.getConsumption());
        when(this.client.exchange(anyString(), any(), any(), eq(UserResponse.class), any()))
                .thenReturn(UserMocks.getUserResponseEntity());
        when(this.client.exchange(anyString(), any(), any(), eq(NourishmentResponse.class), any()))
                .thenReturn(NourishmentMocks.getNourishmentResponseEntity());
        when(this.mapper.mapInNourishmentResponseToNourishmentRequest(any()))
                .thenReturn(NourishmentMocks.getNourishmentRequest());
        when(this.mapper.mapOutConsumptionToConsumptionResponse(any())).thenReturn(consumptionResponse);

        ConsumptionResponse response = consumptionService.create(1L, 1L, ConsumptionMocks.getConsumptionRequest());

        assertNotNull(response);
        assertEquals(consumptionResponse, response);

        verify(this.mapper, times(1)).mapOutConsumptionToConsumptionResponse(any());
        verify(this.client, times(3)).exchange(anyString(), any(), any(), any(), any());
        verify(this.mapper, times(1)).mapOutConsumptionToConsumptionResponse(any());
    }

}