package me.kirenai.re.consumption.util;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;
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
            if (consumption.getUnit() > nourishmentResponse.getUnit()) {
                log.error("Unit exceeded c.unit={} is greater than nr.unit={}", consumption.getUnit(), nourishmentResponse.getUnit());
                throw new IllegalStateException("amount exceeded");
            }
            nourishmentRequest.setUnit(nourishmentResponse.getUnit() - consumption.getUnit());
            if (nourishmentRequest.getUnit() == 0) {
                nourishmentRequest.setUnit(0);
            }
        } else if (Objects.nonNull(consumption.getPercentage())) {
            log.info("Percentage: {}", consumption.getPercentage());
            if (consumption.getPercentage() > nourishmentResponse.getPercentage()) {
                log.error("Percentage exceeded c.percentage={} is greater than nr.percentage={}", consumption.getPercentage(), nourishmentResponse.getPercentage());
                throw new IllegalStateException("percentage exceeded");
            }
            nourishmentRequest.setPercentage(nourishmentResponse.getPercentage() - consumption.getPercentage());
            if (nourishmentRequest.getPercentage() == 0) {
                nourishmentRequest.setPercentage(0);
            }
        }
        return nourishmentRequest;
    }

}