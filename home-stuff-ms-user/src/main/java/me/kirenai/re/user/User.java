package me.kirenai.re.user;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private String fistName;
    private String lastName;
}
