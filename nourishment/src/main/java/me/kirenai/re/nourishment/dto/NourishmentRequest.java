package me.kirenai.re.nourishment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
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
public class NourishmentRequest {

    @NotEmpty
    @Size(min = 2, max = 35)
    private String name;
    @NotEmpty
    private String imageUrl;
    private String description;
    @Valid
    @NotNull
    private NourishmentTypeRequest type;

}