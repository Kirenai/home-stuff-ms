package me.kirenai.re.consumption.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumptionRequest {

    @PositiveOrZero
    @Min(value = 0)
    private Integer unit;
    @PositiveOrZero
    @Max(value = 100)
    @Min(value = 0)
    private Integer percentage;

}
