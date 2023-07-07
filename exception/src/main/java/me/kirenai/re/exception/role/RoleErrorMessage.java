package me.kirenai.re.exception.role;

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
public class RoleErrorMessage extends EntityErrorMessage {

    private String message;

}
