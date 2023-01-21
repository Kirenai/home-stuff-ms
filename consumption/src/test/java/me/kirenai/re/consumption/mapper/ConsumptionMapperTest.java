package me.kirenai.re.consumption.mapper;

import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.util.ConsumptionMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsumptionMapperTest {

    @InjectMocks
    private ConsumptionMapper mapper;
    @Spy
    private ModelMapper modelMapper;

    @Test
    @DisplayName("mapInTest")
    public void mapInConsumptionRequestToConsumptionTest() {
        ConsumptionRequest input = ConsumptionMocks.getConsumptionRequest();
        Consumption request = this.mapper.mapInConsumptionRequestToConsumption(input);

        assertNotNull(request);
        assertNotNull(request.getUnit());

        assertEquals(input.getUnit(), request.getUnit());

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }

    @Test
    @DisplayName("mapOutTest")
    public void mapOutConsumptionToConsumptionResponseTest() {
        Consumption input = ConsumptionMocks.getConsumption();
        ConsumptionResponse response = this.mapper.mapOutConsumptionToConsumptionResponse(input);

        assertNotNull(response);
        assertNotNull(response.getUnit());

        assertEquals(input.getUnit(), response.getUnit());

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }

}