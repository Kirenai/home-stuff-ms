package me.kirenai.re.nourishment.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "nourishmentType")
@JsonSubTypes({
        @Type(value = NourishmentTypePercentageRequest.class, name = "PERCENTAGE"),
        @Type(value = NourishmentTypeUnitRequest.class, name = "UNIT")
})
public interface NourishmentTypeRequest {
}
