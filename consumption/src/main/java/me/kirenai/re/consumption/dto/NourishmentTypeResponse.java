package me.kirenai.re.consumption.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "nourishmentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NourishmentTypePercentageResponse.class, name = "PERCENTAGE"),
        @JsonSubTypes.Type(value = NourishmentTypeUnitResponse.class, name = "UNIT")
})
public interface NourishmentTypeResponse {
}
