package me.kirenai.re.exception.nourishment;

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
public class NourishmentErrorMessage extends EntityErrorMessage {

    private String message;


}
