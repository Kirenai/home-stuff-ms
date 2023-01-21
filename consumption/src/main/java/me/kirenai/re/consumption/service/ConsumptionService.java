package me.kirenai.re.consumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.consumption.dto.UserResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import me.kirenai.re.consumption.util.ConsumptionClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private static final String USER_URL_GET_ONE = "http://localhost:8080/api/users/{userId}";
    private static final String NOURISHMENT_URL_GET_ONE = "http://localhost:8080/api/nourishments/{userId}";

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionMapper mapper;
    private final ConsumptionClient client;

    public List<ConsumptionResponse> findAll(Pageable pageable) {
        log.info("Invoking ConsumptionService.findAll method");
        return this.consumptionRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.mapper::mapOutConsumptionToConsumptionResponse)
                .toList();
    }

    public ConsumptionResponse findOne(Long consumptionId) {
        log.info("Invoking ConsumptionService.findOne method");
        Consumption consumption = this.consumptionRepository.findById(consumptionId)
                .orElseThrow();
        return this.mapper.mapOutConsumptionToConsumptionResponse(consumption);
    }

    public ConsumptionResponse create(Long nourishmentId, Long userId, ConsumptionRequest consumptionDto) {
        log.info("Invoking ConsumptionService.create method");
        Consumption consumption = this.mapper.mapOutConsumptionRequestToConsumption(consumptionDto);
        UserResponse userResponse = this.client.getResponse(userId, USER_URL_GET_ONE, UserResponse.class);
        if (Objects.nonNull(userResponse)) consumption.setUserId(userResponse.getUserId());
        NourishmentResponse nourishmentResponse =
                this.client.getResponse(nourishmentId, NOURISHMENT_URL_GET_ONE, NourishmentResponse.class);
        if (Objects.nonNull(nourishmentResponse)) consumption.setNourishmentId(nourishmentResponse.getNourishmentId());
        this.consumptionRepository.save(consumption);
        return this.mapper.mapOutConsumptionToConsumptionResponse(consumption);
    }

}
