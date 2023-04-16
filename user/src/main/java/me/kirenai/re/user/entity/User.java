package me.kirenai.re.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;

}