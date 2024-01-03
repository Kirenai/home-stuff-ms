package me.kirenai.re.consumption.util;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.*;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;

import java.util.Objects;

@Slf4j
public class ConsumptionProcess {

    public static NourishmentRequest process(Consumption consumption,
                                             NourishmentResponse nourishmentResponse,
                                             ConsumptionMapper mapper) {
        log.info("Invoking ConsumptionProcess.process method");
        NourishmentRequest nourishmentRequest = mapper.mapInNourishmentResponseToNourishmentRequest(nourishmentResponse);

        if (Objects.nonNull(consumption.getUnit())) {
            log.info("Unit: {}", consumption.getUnit());
            NourishmentTypeUnitResponse unitResponse = (NourishmentTypeUnitResponse) nourishmentResponse.getType();
            if (consumption.getUnit() > unitResponse.getUnit()) {
                log.error("Unit exceeded c.unit={} is greater than nr.unit={}", consumption.getUnit(), unitResponse.getUnit());
                throw new IllegalStateException("amount exceeded");
            }
            NourishmentTypeUnitRequest unitRequest = (NourishmentTypeUnitRequest) nourishmentRequest.getType();
            unitRequest.setUnit(unitResponse.getUnit() - consumption.getUnit());
        } else if (Objects.nonNull(consumption.getPercentage())) {
            log.info("Percentage: {}", consumption.getPercentage());
            NourishmentTypePercentageResponse percentageResponse = (NourishmentTypePercentageResponse) nourishmentResponse.getType();
            if (consumption.getPercentage() > percentageResponse.getPercentage()) {
                log.error("Percentage exceeded c.percentage={} is greater than nr.percentage={}", consumption.getPercentage(), percentageResponse.getPercentage());
                throw new IllegalStateException("percentage exceeded");
            }
            NourishmentTypePercentageRequest percentageRequest = (NourishmentTypePercentageRequest) nourishmentRequest.getType();
            percentageRequest.setPercentage(percentageResponse.getPercentage() - consumption.getPercentage());
        }
        return nourishmentRequest;
    }

}