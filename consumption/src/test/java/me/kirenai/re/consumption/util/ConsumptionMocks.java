package me.kirenai.re.consumption.util;

import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.entity.Consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class ConsumptionMocks {

    public static ConsumptionResponse getConsumptionResponse() {
        return getConsumptionResponseList().get(0);
    }

    public static List<ConsumptionResponse> getConsumptionResponseList() {
        return List.of(
                getConsumptionResponseMock(1L, 5, null),
                getConsumptionResponseMock(2L, null, 76),
                getConsumptionResponseMock(3L, 15, null)
        );
    }

    public static ConsumptionResponse getConsumptionResponseMock(Long consumptionId, Integer unit, Integer percentage) {
        return ConsumptionResponse.builder()
                .consumptionId(consumptionId)
                .unit(unit)
                .percentage(percentage)
                .build();
    }

    public static ConsumptionRequest getConsumptionRequest() {
        return ConsumptionRequest.builder()
                .unit(54)
                .build();
    }

    public static Page<Consumption> getConsumptionPage() {
        return new PageImpl<>(List.of(
                getConsumptionMock(1L, 5, null),
                getConsumptionMock(2L, null, 76),
                getConsumptionMock(3L, 15, null)
        ));
    }

    private static Consumption getConsumptionMock(Long consumptionId, Integer unit, Integer percentage) {
        return Consumption.builder()
                .consumptionId(consumptionId)
                .unit(unit)
                .percentage(percentage)
                .build();
    }

    public static Consumption getConsumption() {
        return getConsumptionPage().toList().get(0);
    }
}
