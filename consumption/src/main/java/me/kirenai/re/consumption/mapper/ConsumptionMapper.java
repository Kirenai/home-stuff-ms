package me.kirenai.re.consumption.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.entity.Consumption;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumptionMapper {

    private final ModelMapper modelMapper;

    public ConsumptionResponse mapOutConsumptionToConsumptionResponse(Consumption consumption) {
        log.info("Invoking ConsumptionMapper.mapOutConsumptionToConsumptionResponse method");
        return this.modelMapper.map(consumption, ConsumptionResponse.class);
    }

    public Consumption mapOutConsumptionRequestToConsumption(ConsumptionRequest consumptionRequest) {
        log.info("Invoking ConsumptionMapper.mapOutConsumptionRequestToConsumption method");
        return this.modelMapper.map(consumptionRequest, Consumption.class);
    }

}