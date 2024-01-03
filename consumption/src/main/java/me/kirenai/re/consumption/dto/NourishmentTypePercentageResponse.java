package me.kirenai.re.consumption.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import me.kirenai.re.consumption.enums.NourishmentTypeEnum;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = NourishmentTypePercentageResponse.class)
public class NourishmentTypePercentageResponse implements NourishmentTypeResponse {

    private NourishmentTypeEnum nourishmentType;
    private Integer percentage;

    @JsonCreator
    public NourishmentTypePercentageResponse(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                             @JsonProperty("percentage") Integer percentage) {
        this.nourishmentType = NourishmentTypeEnum.PERCENTAGE;
        this.percentage = percentage;
    }

}
