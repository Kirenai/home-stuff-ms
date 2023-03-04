package me.kirenai.re.consumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.api.NourishmentManager;
import me.kirenai.re.consumption.api.UserManager;
import me.kirenai.re.consumption.dto.*;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import me.kirenai.re.consumption.util.ConsumptionProcess;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionMapper mapper;
    private final UserManager userManager;
    private final NourishmentManager nourishmentManager;

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
        Consumption consumption = this.mapper.mapInConsumptionRequestToConsumption(consumptionDto);
        UserResponse userResponse = this.userManager.findUser(userId);
        if (Objects.nonNull(userResponse)) consumption.setUserId(userResponse.getUserId());
        NourishmentResponse nourishmentResponse = this.nourishmentManager.findNourishment(nourishmentId);
        if (Objects.nonNull(nourishmentResponse)) {
            consumption.setNourishmentId(nourishmentResponse.getNourishmentId());
            NourishmentRequest nourishmentRequest = ConsumptionProcess.process(consumption, nourishmentResponse, this.mapper);
            this.nourishmentManager.updateNourishment(nourishmentRequest, nourishmentResponse);
        }
        this.consumptionRepository.save(consumption);
        return this.mapper.mapOutConsumptionToConsumptionResponse(consumption);
    }

}
