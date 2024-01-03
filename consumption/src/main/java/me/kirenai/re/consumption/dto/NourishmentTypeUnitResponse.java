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
@JsonDeserialize(as = NourishmentTypeUnitResponse.class)
public class NourishmentTypeUnitResponse implements NourishmentTypeResponse {

    private NourishmentTypeEnum nourishmentType;
    private Integer unit;

    @JsonCreator
    public NourishmentTypeUnitResponse(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                       @JsonProperty("unit") Integer unit) {
        this.nourishmentType = NourishmentTypeEnum.UNIT;
        this.unit = unit;
    }

}
