package me.kirenai.re.consumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionMapper mapper;

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
        // TODO: call user and nourishment services
        return this.mapper.mapOutConsumptionToConsumptionResponse(consumption);
    }

}
