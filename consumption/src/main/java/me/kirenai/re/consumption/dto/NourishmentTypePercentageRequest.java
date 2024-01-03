package me.kirenai.re.consumption.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kirenai.re.consumption.enums.NourishmentTypeEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = NourishmentTypePercentageRequest.class)
public class NourishmentTypePercentageRequest implements NourishmentTypeRequest {

    @NotNull
    private NourishmentTypeEnum nourishmentType;
    @Max(value = 100)
    @Min(value = 0)
    @NotNull
    private Integer percentage;

    @JsonCreator
    public NourishmentTypePercentageRequest(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                            @JsonProperty("percentage") Integer percentage) {
        this.nourishmentType = NourishmentTypeEnum.PERCENTAGE;
        this.percentage = percentage;
    }

}
