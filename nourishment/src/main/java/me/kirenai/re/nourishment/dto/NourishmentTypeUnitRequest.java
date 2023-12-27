package me.kirenai.re.nourishment.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kirenai.re.nourishment.enums.NourishmentTypeEnum;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = NourishmentTypeUnitRequest.class)
public class NourishmentTypeUnitRequest implements NourishmentTypeRequest {

    @NotNull
    private NourishmentTypeEnum nourishmentType;
    @Min(value = 0)
    @NotNull
    private Integer unit;

    @JsonCreator
    public NourishmentTypeUnitRequest(@JsonProperty("nourishmentType") NourishmentTypeEnum nourishmentType,
                                      @JsonProperty("unit") Integer unit) {
        this.nourishmentType = NourishmentTypeEnum.UNIT;
        this.unit = unit;
    }

}
