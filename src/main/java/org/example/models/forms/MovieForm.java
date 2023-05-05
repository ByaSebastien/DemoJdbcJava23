package org.example.models.forms;

import lombok.*;
import org.example.models.entities.Book;
import org.example.models.entities.Movie;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieForm {

    private String title;
    private String description;

    public MovieForm(Movie movie){

        this.title = movie.getTitle();
        this.description = movie.getDescription();
    }

    public Movie toEntity(){

        return Movie.builder()
                .title(getTitle())
                .description(getDescription())
                .build();
    }
}
