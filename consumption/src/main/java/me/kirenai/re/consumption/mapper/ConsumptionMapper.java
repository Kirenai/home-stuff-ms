package me.kirenai.re.consumption.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.consumption.entity.Consumption;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Slf4j
@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class ConsumptionMapper {

    public abstract Consumption mapInConsumptionRequestToConsumption(ConsumptionRequest consumptionRequest);

    public abstract ConsumptionResponse mapOutConsumptionToConsumptionResponse(Consumption consumption);

    public abstract NourishmentRequest mapInNourishmentResponseToNourishmentRequest(NourishmentResponse nourishmentResponse);

    @BeforeMapping
    public void mapInLog(ConsumptionRequest consumptionRequest){
        log.info("Invoking ConsumptionMapper.mapInConsumptionRequestToConsumption method");
    }

    @BeforeMapping
    public void mapInLog(NourishmentResponse nourishmentResponse){
        log.info("Invoking ConsumptionMapper.mapInNourishmentResponseToNourishmentRequest method");
    }

    @BeforeMapping
    public void mapOutLog(Consumption consumption){
        log.info("Invoking ConsumptionMapper.mapOutConsumptionToConsumptionResponse method");
    }

}