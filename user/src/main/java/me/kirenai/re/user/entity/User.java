package me.kirenai.re.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    @Column("user_id")
    private Long userId;
    private String username;
    private String password;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private Integer age;

}