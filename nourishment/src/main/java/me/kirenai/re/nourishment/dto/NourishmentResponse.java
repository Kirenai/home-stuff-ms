package me.kirenai.re.nourishment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NourishmentResponse {

    private Long nourishmentId;
    private String name;
    private String imageUrl;
    private String description;
    private Boolean isAvailable;
    private NourishmentTypeResponse type;
    @JsonIgnore
    private Long userId;

}