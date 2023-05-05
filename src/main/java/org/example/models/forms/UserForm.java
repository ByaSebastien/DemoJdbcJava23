package org.example.models.forms;

import lombok.*;
import org.example.models.entities.User;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserForm {

    private String username;
    private String email;
    private String password;

    public UserForm(User user){

        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public User toEntity(){

        return User.builder()
                .username(getUsername())
                .email(getEmail())
                .password(getPassword())
                .build();
    }
}
