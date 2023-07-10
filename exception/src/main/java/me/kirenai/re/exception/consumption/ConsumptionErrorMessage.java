package me.kirenai.re.exception.consumption;

import lombok.*;
import lombok.experimental.SuperBuilder;
import me.kirenai.re.exception.entity.EntityErrorMessage;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionErrorMessage extends EntityErrorMessage {

    private String message;


}
