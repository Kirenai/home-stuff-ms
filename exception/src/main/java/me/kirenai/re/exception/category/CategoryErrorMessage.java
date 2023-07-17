package me.kirenai.re.exception.category;

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
public class CategoryErrorMessage extends EntityErrorMessage {

    private String message;


}
