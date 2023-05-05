package org.example.models.entities;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder @EqualsAndHashCode
public class Author {

    private Integer id;

    private String firstName;

    private String lastName;

    private String pseudo;


    public Author(String firstName, String lastName, String pseudo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudo = pseudo;
    }
}
