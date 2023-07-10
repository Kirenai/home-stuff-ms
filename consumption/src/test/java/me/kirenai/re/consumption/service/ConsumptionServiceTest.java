package me.kirenai.re.consumption.service;

import me.kirenai.re.consumption.api.NourishmentManager;
import me.kirenai.re.consumption.api.UserManager;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import me.kirenai.re.consumption.util.ConsumptionMocks;
import me.kirenai.re.consumption.util.NourishmentMocks;
import me.kirenai.re.consumption.util.UserMocks;
import me.kirenai.re.exception.consumption.ConsumptionNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
    private UserManager userManager;
    @Mock
    private NourishmentManager nourishmentManager;

//    @Test
//    @DisplayName("Should find a consumption list when finding all")
//    void shouldFindConsumptionListWhenFindingAll() {
//        Pageable pageableMock = mock(Pageable.class);
//        List<ConsumptionResponse> consumptionResponse = ConsumptionMocks.getConsumptionResponseList();
//
//        when(this.consumptionRepository.findAll(any(Pageable.class))).thenReturn(ConsumptionMocks.getConsumptionPage());
//        when(this.mapper.mapOutConsumptionToConsumptionResponse(any(Consumption.class)))
//                .thenReturn(consumptionResponse.get(0), consumptionResponse.get(1), consumptionResponse.get(2));
//
//        List<ConsumptionResponse> response = consumptionService.findAll(pageableMock);
//
//        assertEquals(consumptionResponse.size(), response.size());
//        assertEquals(consumptionResponse, response);
//
//        verify(this.consumptionRepository, times(1)).findAll(any(Pageable.class));
//        verify(this.mapper, times(3)).mapOutConsumptionToConsumptionResponse(any());
//    }

    @Test
    @DisplayName("Should find a consumption when finding one")
    void shouldFindConsumptionWhenFindingOne() {
        ConsumptionResponse consumptionResponse = ConsumptionMocks.getConsumptionResponse();

        when(this.consumptionRepository.findById(anyLong())).thenReturn(Mono.just(ConsumptionMocks.getConsumption()));
        when(this.mapper.mapOutConsumptionToConsumptionResponse(any(Consumption.class))).thenReturn(consumptionResponse);

        Mono<ConsumptionResponse> response = consumptionService.findOne(1L);

        StepVerifier
                .create(response)
                .expectNext(consumptionResponse)
                .verifyComplete();

        verify(this.consumptionRepository, times(1)).findById(anyLong());
        verify(this.mapper, times(1)).mapOutConsumptionToConsumptionResponse(any());
    }

    @Test
    @DisplayName("Should throw a consumption not found exception when GET")
    void shouldThrowConsumptionNotFoundExceptionWhenGET_Test() {
        when(this.consumptionRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<ConsumptionResponse> response = consumptionService.findOne(1L);

        StepVerifier
                .create(response)
                .expectError(ConsumptionNotFoundException.class)
                .verify();

        verify(this.consumptionRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Should create a consumption")
    void shouldCreateConsumptionWhenUserHasAuthorizationWhenCreating() {
        ConsumptionResponse consumptionResponse = ConsumptionMocks.getConsumptionResponse();

        when(this.mapper.mapInConsumptionRequestToConsumption(any()))
                .thenReturn(ConsumptionMocks.getConsumption());
        when(this.userManager.findUser(anyLong(), anyString()))
                .thenReturn(Mono.just(UserMocks.getUserResponse()));
        when(this.nourishmentManager.findNourishment(anyLong(), anyString()))
                .thenReturn(Mono.just(NourishmentMocks.getNourishmentResponse()));
        when(this.mapper.mapInNourishmentResponseToNourishmentRequest(any()))
                .thenReturn(NourishmentMocks.getNourishmentRequest());
        when(this.nourishmentManager.updateNourishment(any(), any(), anyString())).thenReturn(Mono.empty());
        when(this.consumptionRepository.save(any())).thenReturn(Mono.just(ConsumptionMocks.getConsumption()));
        when(this.mapper.mapOutConsumptionToConsumptionResponse(any())).thenReturn(consumptionResponse);

        Mono<ConsumptionResponse> response = consumptionService.create(1L, 1L, ConsumptionMocks.getConsumptionRequest(), "Bearer token");

        StepVerifier
                .create(response)
                .expectNext(consumptionResponse)
                .verifyComplete();

        verify(this.mapper, times(1)).mapInConsumptionRequestToConsumption(any());
        verify(this.userManager, times(1)).findUser(anyLong(), anyString());
        verify(this.nourishmentManager, times(1)).findNourishment(anyLong(), anyString());
        verify(this.mapper, times(1)).mapInNourishmentResponseToNourishmentRequest(any());
        verify(this.nourishmentManager, times(1)).updateNourishment(any(), any(), anyString());
        verify(this.consumptionRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapOutConsumptionToConsumptionResponse(any());
    }

}