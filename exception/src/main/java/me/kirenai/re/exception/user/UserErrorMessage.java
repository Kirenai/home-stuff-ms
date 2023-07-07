package me.kirenai.re.exception.user;

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
public class UserErrorMessage extends EntityErrorMessage {

    private String message;


}
