package me.kirenai.re.consumption.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.*;
import me.kirenai.re.consumption.entity.Consumption;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Slf4j
@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class ConsumptionMapper {

    public abstract Consumption mapInConsumptionRequestToConsumption(ConsumptionRequest consumptionRequest);

    public abstract ConsumptionResponse mapOutConsumptionToConsumptionResponse(Consumption consumption);

    @Mapping(target = "type", source = "type", qualifiedByName = "mapInNourishmentTypeRequest")
    public abstract NourishmentRequest mapInNourishmentResponseToNourishmentRequest(NourishmentResponse nourishmentResponse);

    @Named("mapInNourishmentTypeRequest")
    public NourishmentTypeRequest mapInNourishmentTypeRequest(NourishmentTypeResponse nourishmentTypeResponse) {
        if (nourishmentTypeResponse instanceof NourishmentTypeUnitResponse nourishmentTypeUnitResponse) {
            return this.mapInNourishmentTypeUnitRequest(nourishmentTypeUnitResponse);
        }
        if (nourishmentTypeResponse instanceof NourishmentTypePercentageResponse nourishmentTypePercentageResponse) {
            return this.mapInNourishmentTypePercentageRequest(nourishmentTypePercentageResponse);
        }
        return null;
    }

    abstract NourishmentTypeUnitRequest mapInNourishmentTypeUnitRequest(NourishmentTypeUnitResponse nourishmentTypeUnitResponse);

    abstract NourishmentTypePercentageRequest mapInNourishmentTypePercentageRequest(NourishmentTypePercentageResponse nourishmentTypePercentageResponse);

    @BeforeMapping
    public void mapInLog(ConsumptionRequest consumptionRequest) {
        log.info("Invoking ConsumptionMapper.mapInConsumptionRequestToConsumption method");
    }

    @BeforeMapping
    public void mapInLog(NourishmentResponse nourishmentResponse) {
        log.info("Invoking ConsumptionMapper.mapInNourishmentResponseToNourishmentRequest method");
    }

    @BeforeMapping
    public void mapOutLog(Consumption consumption) {
        log.info("Invoking ConsumptionMapper.mapOutConsumptionToConsumptionResponse method");
    }

}