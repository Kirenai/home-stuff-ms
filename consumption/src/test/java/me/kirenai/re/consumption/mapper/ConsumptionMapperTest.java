package me.kirenai.re.consumption.mapper;

import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.util.ConsumptionMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsumptionMapperTest {

    private final ConsumptionMapper mapper = Mappers.getMapper(ConsumptionMapper.class);

    @Test
    @DisplayName("mapInTest")
    public void mapInConsumptionRequestToConsumptionTest() {
        ConsumptionRequest input = ConsumptionMocks.getConsumptionRequest();
        Consumption request = this.mapper.mapInConsumptionRequestToConsumption(input);

        assertNotNull(request);
        assertNotNull(request.getUnit());

        assertEquals(input.getUnit(), request.getUnit());
    }

    @Test
    @DisplayName("mapOutTest")
    public void mapOutConsumptionToConsumptionResponseTest() {
        Consumption input = ConsumptionMocks.getConsumption();
        ConsumptionResponse response = this.mapper.mapOutConsumptionToConsumptionResponse(input);

        assertNotNull(response);
        assertNotNull(response.getUnit());

        assertEquals(input.getUnit(), response.getUnit());
    }

}